package com.nnk.springboot.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // permet d' importer la configuration Spring Security personnalisée
@EnableMethodSecurity(prePostEnabled = true) //active la sécurité au niveau des méthode dans l' application
public class SecurityConfig {
	
	private static final Logger logger = LogManager.getLogger("SpringSecurityConfig");
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
/**
 * La classe HttpSecurity est sollicitée pour appliquer la chaîne de filtres de sécurité aux requêtes HTTP. 
 * Par défaut, les paramètres de sécurité fonctionnent sur toutes les requêtes.
 * @param http
 * @return
 * @throws Exception
 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {		
	    return http.authorizeHttpRequests(auth -> {
	    	logger.info("login securityFilterChain");
	    	auth.requestMatchers("/css/**").permitAll();
	    	auth.requestMatchers("/error").permitAll();
	        auth.requestMatchers("/admin").hasRole("ADMIN"); // gère le login
	        auth.requestMatchers("/user").hasRole("USER");
	    	auth.anyRequest().authenticated();
	        logger.info("logout securityFilterChain");
	    }) .formLogin(form -> form
	    	    .loginPage("/login")   
	    	    .permitAll()
	    	  )
	        .logout(logout -> logout // ici on gère la déconnexion
	            .logoutUrl("/") // URL de déconnexion
	            .logoutSuccessUrl("/") // URL de redirection après déconnexion
	            .invalidateHttpSession(true) // invalider la session HTTP
	            .deleteCookies("JSESSIONID") // supprimer les cookies de session
	        )
	        .build();
	}

	/**
	 * Permet l'authentification + mdp encodé de l'utilisateur
	 * @param http
	 * @param bCryptPasswordEncoder
	 * @return
	 * @throws Exception
	 */
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
	    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
	    authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
	    return authenticationManagerBuilder.build();
	}
	
}