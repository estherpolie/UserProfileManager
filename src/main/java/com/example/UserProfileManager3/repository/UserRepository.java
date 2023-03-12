package com.example.UserProfileManager3.repository;


import com.example.UserProfileManager3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query(value = "SELECT * FROM users s WHERE s.email = ?1", nativeQuery = true)
    Optional<User> findUserByEmail(String email);
}