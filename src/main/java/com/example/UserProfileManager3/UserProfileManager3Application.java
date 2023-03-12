package com.example.UserProfileManager3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.UserProfileManager3.entity")
public class UserProfileManager3Application {

	public static void main(String[] args) {
		SpringApplication.run(UserProfileManager3Application.class, args);
	}

}
