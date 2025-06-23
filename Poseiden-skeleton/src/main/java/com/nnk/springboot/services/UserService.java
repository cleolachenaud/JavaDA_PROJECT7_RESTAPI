package com.nnk.springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class UserService {
	private static final Logger logger = LogManager.getLogger("UserService");

	@Autowired
	private UserRepository userRepository;
	
	
	public Iterable<User> getUserList() {
        return userRepository.findAll();
    }
	
	public User addUser(User user) {		
        return userRepository.save(user);
    }
	
	public User updateUser (User user) {
		 // je vérifie que le user existe, si il n'existe pas je remonte une exception
       if(!userRepository.existsById(user.getId())){
    	   logger.error("user inconnu");
    	   throw new RuntimeException("user n'existe pas en base de données ");
       }
       // si il existe, j'insère le nouveau curvePoint en bdd. 
		return userRepository.save(user);
	}
	
	public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }
	public void deleteUserById(Integer id) {
		 // je vérifie que le user existe
		userRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("user inconnu en base"));
		userRepository.deleteById(id);
    }

}
