package com.example.postproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class PostProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostProjectApplication.class, args);
	}

}