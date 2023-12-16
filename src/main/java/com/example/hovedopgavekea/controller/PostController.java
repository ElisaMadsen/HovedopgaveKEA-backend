package com.example.hovedopgavekea.controller;

import com.example.hovedopgavekea.model.FieldOfStudy;
import com.example.hovedopgavekea.model.Post;
import com.example.hovedopgavekea.model.PostDTO;
import com.example.hovedopgavekea.model.User;
import com.example.hovedopgavekea.service.IFieldOfStudyService;
import com.example.hovedopgavekea.service.IPostService;
import com.example.hovedopgavekea.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    private IPostService postService;
    private IUserService userService;
    private IFieldOfStudyService fieldOfStudyService;


    public PostController(IPostService postService, IUserService userService, IFieldOfStudyService fieldOfStudyService) {
        this.postService = postService;
        this.userService = userService;
        this.fieldOfStudyService = fieldOfStudyService;
    }

    @GetMapping("/posts/all")
    @CrossOrigin
    public ResponseEntity<List<PostDTO>> getAllPosts(){
        Set<Post> allPosts = postService.findAll();
        List<PostDTO> postDTOs = new ArrayList<>();

        allPosts.stream()
                .sorted(Comparator.comparing(Post::getPostDate).reversed()) // Sort by post date in ascending order
                .forEach(post -> {
                    PostDTO postDTO = new PostDTO();
                    Long userId = post.getUser().getUserId();
                    User user = userService.findById(userId).get();
                    Long fieldOfStudyId = user.getFieldOfStudy().getFieldOfStudyId();
                    FieldOfStudy fieldOfStudy = fieldOfStudyService.findById(fieldOfStudyId).get();

                    postDTO.setPostId(post.getPostId());
                    postDTO.setPostTitle(post.getPostTitle());
                    postDTO.setPost(post.getPost());
                    postDTO.setPostDate(post.getPostDate());
                    postDTO.setUserId(userId);
                    postDTO.setUserName(user.getUserName());

                    postDTO.setFieldOfStudyId(fieldOfStudyId);
                    postDTO.setFieldOfStudyName(fieldOfStudy.getFieldOfStudyName());

                    postDTO.setGraduationYear(user.getGraduationYear());
                    postDTO.setUserEmail(user.getUserEmail());
                    postDTO.setUserPassword(user.getUserPassword());
                    postDTOs.add(postDTO);
                });
        return new ResponseEntity<>(postDTOs, HttpStatus.OK);
    }


    @PostMapping("/createPost")
    @CrossOrigin
    public ResponseEntity<Post> createPost(@RequestBody PostDTO postDTO) {
            // Extract user information from the incoming PostDTO
            String userEmail = postDTO.getUserEmail();
            String userPassword = postDTO.getUserPassword();

            // Retrieve the logged-in user based on the provided email and password
            Optional<User> loggedInUser = userService.findByUserEmailAndUserPassword(userEmail, userPassword);

            if (loggedInUser.isPresent()) {
                // Create the Post object
                Post post = new Post();
                post.setPostTitle(postDTO.getPostTitle());
                post.setPost(postDTO.getPost());
                post.setPostDate(String.valueOf(LocalDateTime.now()));

                // Associate the user with the post
                post.setUser(loggedInUser.get());

                // Save the post
                Post savedPost = postService.save(post);

                return ResponseEntity.ok(savedPost);
            } else {
                // Return a response indicating that the user is not logged in
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
    }


    @PutMapping("/updatePost")
    @CrossOrigin
    public ResponseEntity<?> updatePost(@RequestBody Post post) {
        Long postId = post.getPostId();
        Optional<Post> postToUpdate = postService.findById(postId);

        if (postToUpdate.isPresent()) {
            Post updatedPost = postToUpdate.get();
            updatedPost.setPostId(post.getPostId());
            updatedPost.setPostTitle(post.getPostTitle());
            updatedPost.setPost(post.getPost());
            updatedPost.setPostDate(post.getPostDate());

            Post savedPost = postService.save(post);

            if (savedPost == null) {
                return new ResponseEntity<>("Failed to update post", HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(savedPost, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("Couldn't find post", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id){

        Optional<Post> post = postService.findById(id);
        Long postId = post.get().getUser().getUserId();
        Optional<User> user = userService.findById(postId);
        Long fieldOfStudyId = user.get().getFieldOfStudy().getFieldOfStudyId();
        Optional<FieldOfStudy>  fieldOfStudy = fieldOfStudyService.findById(fieldOfStudyId);

            PostDTO postDTO = new PostDTO();

            postDTO.setPostId(id);
            postDTO.setPostTitle(post.get().getPostTitle());
            postDTO.setPost(post.get().getPost());
            postDTO.setPostDate(post.get().getPostDate());

            postDTO.setUserId(user.get().getUserId());
            postDTO.setUserName(user.get().getUserName());

            postDTO.setFieldOfStudyId(fieldOfStudy.get().getFieldOfStudyId());
            postDTO.setFieldOfStudyName(fieldOfStudy.get().getFieldOfStudyName());

            postDTO.setGraduationYear(user.get().getGraduationYear());
            postDTO.setUserEmail(user.get().getUserEmail());
            postDTO.setUserPassword(user.get().getUserPassword());
            return new ResponseEntity<>(postDTO, HttpStatus.OK);
        }

    @DeleteMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<Post> deletePost(@PathVariable Long id){
        postService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
