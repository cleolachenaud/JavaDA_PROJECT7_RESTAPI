package com.nnk.springboot.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nnk.springboot.configuration.ConstantesUtils;
import com.nnk.springboot.domain.User;
/**
 * Classe permettant de vérifier que le mot de passe est valide, respecte bien les exigences demandées et de l'encoder avant son insertion en bdd. 
 */
@Service
public class ValidationPassword {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static final Logger logger = LogManager.getLogger("ValidationPasswordLogger");
	/**
	 * boolean qui retourne vrai ou faux sur le rejex du mot de passe 
	 * utilisé par la méthode validationEncodagePassword
	 * @param password
	 * @return
	 */
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

	/**
	 * méthode qui encode le mot de passe. Préalablement cette méthode réalise une vérification afin de savoir
	 * si le mot de passe saisi respecte bien les contraintes demandées. 
	 * @param user
	 * @return
	 */
	public String validationEtEncodagePassword(User user) {
		// je vérifie que mon mot de passe est raccord avec les règles de sécurité mises en place
        if(!isValid(user.getPassword())) {
        	logger.error("mot de passe trop simple");
    		throw new IllegalArgumentException(ConstantesUtils.PASSWORD_ERREUR);
    	}
	    String encodedPassword = passwordEncoder.encode(user.getPassword());
		return encodedPassword;
	}
	
}
