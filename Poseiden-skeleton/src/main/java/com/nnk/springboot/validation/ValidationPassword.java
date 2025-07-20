package com.nnk.springboot.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;

@Service
public class ValidationPassword {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static final Logger logger = LogManager.getLogger("ValidationPasswordLogger");
	
	private boolean isValid(String password) { 
        if (password == null) {
        	return false;
        }
        // Regex pour au moins 8 caractères, 1 chiffre, 1 majuscule, 1 caractère spécial
        String pattern = "^(?=.*[0-9])"           // au moins un chiffre
                       + "(?=.*[A-Z])"            // au moins une majuscule
                       + "(?=.*[@#$%^&+=!])"      // au moins un caractère spécial parmi ceux-ci
                       + "(?=\\S+$)"              // pas d'espace
                       + ".{8,}$";                // au moins 8 caractères

        return password.matches(pattern);
    }

	
	public String validationEtEncodagePassword(User user) {
		// je vérifie que mon mot de passe est raccord avec les règles de sécurité mises en place
        if(!isValid(user.getPassword())) {
        	logger.error("mot de passe trop simple");
    		throw new IllegalArgumentException("le mot de passe est trop simple ! Veuillez en choisir un plus complexe !");
    	}
	    String encodedPassword = passwordEncoder.encode(user.getPassword());
		return encodedPassword;
	}
	
}
