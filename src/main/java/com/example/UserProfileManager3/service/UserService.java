package com.example.UserProfileManager3.service;

import com.example.UserProfileManager3.Exception.NotFoundException;
import com.example.UserProfileManager3.entity.User;
import com.example.UserProfileManager3.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    public User save(User user) {
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));//save as hash
        //        user.setPassword(passwordEncoder.encode(user .getPassword()));
//        PasswordEncoder passwordEncoder = new MessageDigestPasswordEncoder("SHA-256");
//        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public List<User> getAllUsers()//list all Users
    {
        List<User> allSales =userRepository.findAll();
        return allSales;
    }

    public Optional<User> findById(Long id)//find user by id
    {
        return userRepository.findById(id);
    }
    public String deleteUserById(Long id)//delete user by id
    {
         userRepository.deleteById(id);
         return "deleted successfully";
    }
    //need to implement find by name

    public User updateUserById(Long id, User user) {   //edit user by id
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            User existingUser = userData.get();
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setPassword(user.getPassword());

            existingUser.setEmail(user.getEmail());

            User updatedUser = userRepository.save(existingUser);
            return updatedUser;
        } else {
            throw new NotFoundException("User not found with ID: " + id);
        }
    }

    public Optional<User> findUserByEmail(String email)//find user by email
    {
        return userRepository.findUserByEmail(email);
    }



    public String loginUser(User users) {
        User user = userRepository.findUserByEmail(users.getEmail()).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
//        if(!bCryptPasswordEncoder.matches(users.getPassword(),user.getPassword()))

        if (!user.getPassword().equals(users.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }
        return "logged in successfully";
}


}


