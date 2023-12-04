package com.example.hovedopgavekea.controller;

import com.example.hovedopgavekea.model.User;
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

    public UserController (IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/all")
    public ResponseEntity<Set<User>> getAllUsers(){
        return new ResponseEntity(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user) {

        if (userService.save(user) !=null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
        }
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


    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id){
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
