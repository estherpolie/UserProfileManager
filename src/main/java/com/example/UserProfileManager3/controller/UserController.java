package com.example.UserProfileManager3.controller;


import com.example.UserProfileManager3.entity.User;
import com.example.UserProfileManager3.filter.JwtUtil;
import com.example.UserProfileManager3.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {


    @Autowired
    private UserService userService;

    public UserController(UserService userService)
    {
        this.userService=userService;
    }


    @PostMapping("/api/users/signup/")
    public ResponseEntity<Object> createUser(@RequestBody User user) {

            if (userService.findUserByEmail(user.getEmail()).isPresent()) {
            return new ResponseEntity<>("User email exist  ", HttpStatus.NOT_FOUND);
        }
        else {
            userService.save(user);
            return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<String> loginUser(@RequestBody User users) {
        User user = userService.findUserByEmail(users.getEmail()).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.UNAUTHORIZED);
        }
   //     String secretKey = "mySecretKey";
//
//        // Create a JWT using the builder pattern
//        String jwt = Jwts.builder().claim("id",user.getId())
//                .signWith(SignatureAlgorithm.HS256, secretKey) // Sign the JWT with the secret key
//                .compact(); // Compact the JWT into a string

//       Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//
//
//        String token = Jwts.builder().claim("id",user.getId())
//                .signWith(key)
//                .compact();
        String jwt = JwtUtil.generateToken(users.getEmail());

        if (!users.getPassword().equals(user.getPassword())) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.UNAUTHORIZED);
        }


        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }


    @GetMapping("/api/users/signup/all")
    public List<User> getAllUser( User user) {
        return userService.getAllUsers();
    }


    @GetMapping("api/users/{id}")//FIND user by id
    public Optional<User> userId (@PathVariable Long id)
    {
        return userService.findById(id);
    }

    @DeleteMapping("api/{id}")//delete by userId
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "deleted successfully";
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
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //if user id doesnt exist (404 not found)
        }
    }
}



