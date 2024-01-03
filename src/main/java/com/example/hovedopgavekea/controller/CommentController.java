package com.example.hovedopgavekea.controller;

import com.example.hovedopgavekea.model.*;
import com.example.hovedopgavekea.service.ICommentService;
import com.example.hovedopgavekea.service.IPostService;
import com.example.hovedopgavekea.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/comment")
public class CommentController {

    private ICommentService commentService;
    private IPostService postService;

    private IUserService userService;

    public CommentController(ICommentService commentService,IPostService postService,IUserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }


    @GetMapping("/comments/all")
    public ResponseEntity<Set<Comment>> getAllComments() {
        return new ResponseEntity(commentService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/createComment/{postId}")
    @CrossOrigin
    public ResponseEntity<Comment> createComment(@PathVariable Long postId, @RequestBody CommentDTO commentDto) {
        String userEmail = commentDto.getUserEmail();
        String userPassword = commentDto.getUserPassword();

        // Retrieve the logged-in user based on the provided email and password
        Optional<User> loggedInUser = userService.findByUserEmailAndUserPassword(userEmail, userPassword);
        Optional<Post> post = postService.findById(postId);

        if (loggedInUser.isPresent() && post.isPresent()) {
            Comment savedComment = new Comment();
            savedComment.setComment(commentDto.getComment());
            // savedComment.setCommentDate(commentDto.getCommentDate());
            savedComment.setCommentDate(String.valueOf(LocalDateTime.now()));
            savedComment.setUser(loggedInUser.get());
            savedComment.setPost(post.get());

            Comment saveComment = commentService.save(savedComment);

            return ResponseEntity.ok(saveComment);
        } else {
            // Return a response indicating that the user is not logged in
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    /*
    @GetMapping("/comments/all")
    public ResponseEntity<Set<Comment>> getAllComments() {
        return new ResponseEntity(commentService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/createComment/{postId}")
    @CrossOrigin
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        String userEmail = comment.getUser().getUserEmail();
        String userPassword = comment.getUser().getUserPassword();

        // Retrieve the logged-in user based on the provided email and password
        Optional<User> loggedInUser = userService.findByUserEmailAndUserPassword(userEmail, userPassword);

        if (loggedInUser.isPresent()) {
            Comment savedComment = new Comment();
            comment.setComment(comment.getComment());
            comment.setCommentDate(String.valueOf(LocalDateTime.now()));

            savedComment.setUser(loggedInUser.get());

            Comment saveComment = commentService.save(savedComment);

            return ResponseEntity.ok(saveComment);
        } else {
            // Return a response indicating that the user is not logged in
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

*/
    // Example endpoint for getting comments of a post
    @GetMapping("/{postId}/comments")
    public ResponseEntity<Set<Comment>> getPostComments(@PathVariable Long postId) {
        // Retrieve the post based on postId
        Optional<Post> postOptional = postService.findById(postId);

        return postOptional.map(post -> new ResponseEntity<>(post.getComments(), HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        return new ResponseEntity(commentService.findById(id), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable Long id) {
        commentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
