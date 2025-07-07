package com.nnk.springboot.configuration;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import com.nnk.springboot.repositories.UserRepository;

import org.springframework.security.core.userdetails.UserDetailsService;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	private static final Logger logger = LogManager.getLogger("CustomUserDetailsService");

	@Autowired
    private UserRepository usersRepository;
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("entrée dans la méthode loadUserByUsername");
		com.nnk.springboot.domain.User user = usersRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User notfound");
		}
		User userReturn = new User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user.getRole())); 
        logger.info("sortie de la méthode loadUserByUsername" + userReturn.toString());
        return userReturn; 
    }

    private List<GrantedAuthority> getGrantedAuthorities(String role) {
    	logger.info("entrée dans la méthode getGrantedAuthorities");
    	//role = "user";
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(role));
        logger.info("sortie de la méthode getGrantedAuthorities (" + role + ")");
        return authorities;
    }
	    
	    
	
}
