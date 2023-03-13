package com.example.UserProfileManager3.controller;


import com.example.UserProfileManager3.entity.User;
import com.example.UserProfileManager3.filter.JwtUtil;
import com.example.UserProfileManager3.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.spec.SecretKeySpec;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {


    @Autowired
    private UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService)
    {
        this.userService=userService;
    }


    @PostMapping("/api/users/signup/")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {

            if (userService.findUserByEmail(user.getEmail()).isPresent()) {
                logger.info("user email exist ");
            return new ResponseEntity<>("User email exist  ", HttpStatus.NOT_FOUND);
        }
        else {
            userService.save(user);
                logger.info("user  saved successfully =>{}",user);

                return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<String> loginUser(@RequestBody User users) {
        User user = userService.findUserByEmail(users.getEmail()).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.UNAUTHORIZED);
        }


        if (!users.getPassword().equals(user.getPassword())) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.UNAUTHORIZED);
        }

        logger.info("user  logged in successfully =>{}",users);
        return new ResponseEntity<>(userService.loginUser(users), HttpStatus.OK);
    }


    @GetMapping("/api/users/signup/all")
    public List<User> getAllUser( User user) {
        logger.info("users  retrieved successfully =>{}",userService.getAllUsers());

        return userService.getAllUsers();
    }


    @GetMapping("api/users/{id}")//FIND user by id
    public Optional<User> userId (@PathVariable Long id)
    {
        logger.info("user  retrieved successfully =>{}",userService.findById(id));

        return userService.findById(id);
    }

    @DeleteMapping("api/{id}")//delete by userId
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            userService.deleteUserById(id);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
            // User exists
        } else {
            // User does not exist
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

        }


    }

    @PutMapping("api/1/{id}")//update user by id
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        Optional<User> userData = userService.findById(id);

        if (userData.isPresent()) {
            User existingUser = userData.get();
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setPassword(user.getPassword());
            existingUser.setEmail(user.getEmail());
            User updatedUser = userService.save(existingUser);
            return new ResponseEntity<>(userService.updateUserById(id,updatedUser), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //if user id doesnt exist (404 not found)
        }
    }
}



