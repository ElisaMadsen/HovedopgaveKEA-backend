package com.example.hovedopgavekea.controller;

import com.example.hovedopgavekea.model.FieldOfStudy;
import com.example.hovedopgavekea.model.LoginRequest;
import com.example.hovedopgavekea.model.Post;
import com.example.hovedopgavekea.model.User;
import com.example.hovedopgavekea.service.IFieldOfStudyService;
import com.example.hovedopgavekea.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
public class UserController {

    private IUserService userService;
    private IFieldOfStudyService fieldOfStudyService;

    public UserController (IUserService userService, IFieldOfStudyService fieldOfStudyService) {
        this.userService = userService;
        this.fieldOfStudyService = fieldOfStudyService;
    }

    @GetMapping("/users/all")
    public ResponseEntity<Set<User>> getAllUsers(){
        return new ResponseEntity(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user) {

        if(userService.save(user) != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody LoginRequest loginRequest) {
        Optional<User> loggedInUser = userService.findByUserEmailAndUserPassword(
                loginRequest.getUserEmail(),
                loginRequest.getUserPassword()
        );

        return loggedInUser.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }

    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Long userId = user.getUserId();
        Optional<User> userToUpdate = userService.findById(userId);

        if (userToUpdate.isPresent()) {
            User updatedUser = userToUpdate.get();
            updatedUser.setUserId(user.getUserId());
            updatedUser.setUserName(user.getUserName());
            updatedUser.setFieldOfStudy(user.getFieldOfStudy());
            updatedUser.setGraduationYear(user.getGraduationYear());
            updatedUser.setUserEmail(user.getUserEmail());
            updatedUser.setUserPassword(user.getUserPassword());

            User savedUser = userService.save(user);

            if (savedUser == null) {
                return new ResponseEntity<>("Failed to update user", HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(savedUser, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("Couldn't find user", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return new ResponseEntity(userService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/{userId}/posts")
    public ResponseEntity<Set<Post>> getUserPosts(@PathVariable Long userId) {
        Optional<User> user = userService.findById(userId);

        if (user.isPresent()) {
            Set<Post> userPosts = user.get().getPosts();
            return new ResponseEntity<>(userPosts, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id){
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
