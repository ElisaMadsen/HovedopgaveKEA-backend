package com.example.hovedopgavekea.controller;

import com.example.hovedopgavekea.model.*;
import com.example.hovedopgavekea.service.IFieldOfStudyService;
import com.example.hovedopgavekea.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        try {
            // Create a new User entity from the UserDTO
            User newUser = new User();
            newUser.setUserName(userDTO.getUserName());
            newUser.setGraduationYear(userDTO.getGraduationYear());
            newUser.setUserEmail(userDTO.getUserEmail());
            newUser.setUserPassword(userDTO.getUserPassword());

            // Retrieve the FieldOfStudy entity based on the fieldOfStudyId from UserDTO
            Optional<FieldOfStudy> fieldOfStudyOptional = fieldOfStudyService.findById(userDTO.getFieldOfStudyId());
            if (fieldOfStudyOptional.isPresent()) {
                FieldOfStudy fieldOfStudy = fieldOfStudyOptional.get();

                // Set the FieldOfStudy for the User entity
                newUser.setFieldOfStudy(fieldOfStudy);

                // Save the new User entity
                User savedUser = userService.save(newUser);

                return ResponseEntity.ok(savedUser);
            } else {
                // Return a response indicating that the field of study was not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            // Handle any exceptions that might occur during user creation
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody LoginRequest loginRequest) {
        Optional<User> loggedInUser = userService.findByUserEmailAndUserPassword(
                loginRequest.getUserEmail(),
                loginRequest.getUserPassword()
        );

        if(loggedInUser.isPresent()){
            System.out.println(loggedInUser.get().getUserId());
        }

        return loggedInUser.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO){
        Long userId = userDTO.getUserId();
        Optional<User> userToUpdate = userService.findById(userId);
        Optional<FieldOfStudy> fieldOfStudyToUpdate = fieldOfStudyService.findById(userDTO.getFieldOfStudyId());
        if (userToUpdate.isPresent() && fieldOfStudyToUpdate.isPresent()) {
            FieldOfStudy fieldOfStudy = fieldOfStudyToUpdate.get();
            fieldOfStudy.setFieldOfStudyId(fieldOfStudy.getFieldOfStudyId());
            fieldOfStudy.setFieldOfStudyName(userDTO.getFieldOfStudyName());
            FieldOfStudy savedFieldOfStudy = fieldOfStudyService.save(fieldOfStudy);

            User user = userToUpdate.get();
            user.setUserId(user.getUserId());
            user.setUserName(user.getUserName());
            user.setGraduationYear(userDTO.getGraduationYear());
            user.setUserEmail(user.getUserEmail());
            user.setUserPassword(user.getUserPassword());
            user.setFieldOfStudy(savedFieldOfStudy);
            User savedUser = userService.save(user);

            if (savedUser == null) {
                return new ResponseEntity<>("Fejl i opdatering af bruger", HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(savedUser, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("Kunne ikke finde bruger", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<FieldOfStudy> getUserFieldOfStudy(@PathVariable Long userId) {
        Optional<User> userOptional = userService.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            FieldOfStudy fieldOfStudy = user.getFieldOfStudy();
            if (fieldOfStudy != null) {
                return new ResponseEntity<>(fieldOfStudy, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
