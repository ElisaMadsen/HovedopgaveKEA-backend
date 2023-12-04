package com.example.hovedopgavekea.controller;

import com.example.hovedopgavekea.model.Post;
import com.example.hovedopgavekea.model.PostDTO;
import com.example.hovedopgavekea.model.User;
import com.example.hovedopgavekea.service.IPostService;
import com.example.hovedopgavekea.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/post")

public class PostController {

    private IPostService postService;
    private IUserService userService;

    public PostController(IPostService postService, IUserService userService) {
        this.postService = postService;
        this.userService = userService;

    }

    @GetMapping("/posts/all")
    public ResponseEntity<List<PostDTO>> getAllPosts(){
        Set<Post> allPosts = postService.findAll();
        List<PostDTO> postDTOs = new ArrayList<>();

        allPosts.forEach(post -> {
            PostDTO postDTO = new PostDTO();
            Long id = post.getUser().getUserId();
            User user = userService.findById(id).get();
            postDTO.setPostId(post.getPostId());
            postDTO.setPostTitle(post.getPostTitle());
            postDTO.setPost(post.getPost());
            postDTO.setPostDate(post.getPostDate());
            postDTO.setUserId(id);
            postDTO.setUserName(user.getUserName());
            postDTO.setFieldOfStudy(user.getFieldOfStudy());
            postDTO.setGraduationYear(user.getGraduationYear());
            postDTO.setUserEmail(user.getUserEmail());
            postDTO.setUserPassword(user.getUserPassword());
            postDTOs.add(postDTO);
        });
        return new ResponseEntity<>(postDTOs, HttpStatus.OK);
    }

    @PostMapping("/createPost")
    public ResponseEntity<String> createPost(@RequestBody Post post, @RequestParam Long userId) {
        Optional<User> user = userService.findById(userId);
        if (user.isPresent()) {
            post.setUser(user.get());
            postService.save(post);
            return new ResponseEntity<>("Post oprettet", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User ikke fundet " + userId, HttpStatus.OK);
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
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id){

        Optional<Post> post = postService.findById(id);
        Long postId = post.get().getUser().getUserId();
        Optional<User> user = userService.findById(postId);
        PostDTO postDTO = new PostDTO();

        postDTO.setPostId(id);
        postDTO.setPostTitle(post.get().getPostTitle());
        postDTO.setPost(post.get().getPost());
        postDTO.setPostDate(post.get().getPostDate());
        postDTO.setUserId(user.get().getUserId());
        postDTO.setUserName(user.get().getUserName());
        postDTO.setFieldOfStudy(user.get().getFieldOfStudy());
        postDTO.setGraduationYear(user.get().getGraduationYear());
        postDTO.setUserEmail(user.get().getUserEmail());
        postDTO.setUserPassword(user.get().getUserPassword());
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<Post> deletePost(@PathVariable Long id){
        postService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
