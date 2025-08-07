package com.nnk.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nnk.springboot.repositories.UserRepository;

@SpringBootApplication
public class Application {
	@Autowired UserRepository userRepository;  

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
