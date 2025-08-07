package com.nnk.springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.configuration.ConstantesUtils;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.validation.ValidationPassword;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * classe du service de l'identité de USER permet d'appeler les méthodes de création, modification, suppression 
 */
@Service
public class UserService {
	private static final Logger logger = LogManager.getLogger("UserService");
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ValidationPassword validationPassword;
	
	public Iterable<User> getUserList() {
        return userRepository.findAll();
    }
	
	public User addUser(User user) {
	    logger.info("entrée dans la methode addUser");
	    
	    if (userRepository.existsByUsername(user.getUsername())) {
	        logger.error("username déjà connu de la base de donnees");
	        throw new IllegalArgumentException(ConstantesUtils.USER_EXIST);
	    }
	   
	    String encodedPassword = validationPassword.validationEtEncodagePassword(user);

	    User userCreate = new User();
	    userCreate.setUsername(user.getUsername());
	    userCreate.setPassword(encodedPassword);
	    userCreate.setFullname(user.getFullname());
	    userCreate.setRole(user.getRole());

	    logger.info("utilisateur inséré en base, fin de la methode inscriptionUser " + userCreate.toString());
	    return userRepository.save(userCreate);
	}
	
	public User updateUser (User user) {
		 // je vérifie que le user existe, si il n'existe pas je remonte une exception
       logger.info("entrée dans la méthode updateUser de UserService");
		if(!userRepository.existsById(user.getId())){
    	   logger.error("user inconnu");
    	   throw new RuntimeException(ConstantesUtils.USER_NOTFOUND); 
		}
		if((user.getPassword() != null && !user.getPassword().isEmpty())) {
			String bCryptPasswordEncoderUpdate = validationPassword.validationEtEncodagePassword(user);
			user.setPassword(bCryptPasswordEncoderUpdate);
		}
		return userRepository.save(user);
	}
	
	public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }
	public void deleteUserById(Integer id) {
		 // je vérifie que le user existe
		userRepository.findById(id)
			.orElseThrow(() -> new RuntimeException(ConstantesUtils.USER_NOTFOUND));
		userRepository.deleteById(id);
    }
	


}
