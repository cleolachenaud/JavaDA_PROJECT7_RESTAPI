package com.nnk.springboot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


import com.nnk.springboot.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private static final Logger logger = LogManager.getLogger("SpringSecurityConfig");
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
    private UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {		
	    return http.authorizeHttpRequests(auth -> {
	    	logger.info("login securityFilterChain");
	        auth.requestMatchers("/admin").hasRole("ADMIN"); // gère le login
	        auth.requestMatchers("/user").hasRole("USER");
	        auth.requestMatchers("/user/*").permitAll();
	        auth.requestMatchers("/").permitAll();
	        auth.anyRequest().authenticated();
	        logger.info("logout securityFilterChain");
	    }).formLogin(Customizer.withDefaults())
	        .logout(logout -> logout // ici on gère la déconnexion
	            .logoutUrl("/logout") // URL de déconnexion
	            .logoutSuccessUrl("/login?logout") // URL de redirection après déconnexion
	            .invalidateHttpSession(true) // invalider la session HTTP
	            .deleteCookies("JSESSIONID") // supprimer les cookies de session
	        )
	        .build();
	}
  

	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
	    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
	    authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
	    return authenticationManagerBuilder.build();
	}
	/*
    @Bean
	public UserDetailsService users() {
	    UserDetails user = User.withDefaultPasswordEncoder()
	        .username("user")
	        .password("password")
	        .roles("USER")
	        .build();
	    return new InMemoryUserDetailsManager(user);
	}*/
}