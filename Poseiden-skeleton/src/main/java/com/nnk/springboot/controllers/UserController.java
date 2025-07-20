package com.nnk.springboot.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
	@Autowired
	UserService userService;
    
    private static final Logger logger = LogManager.getLogger("UserController");
    private static final String USER_ERREUR = "le user comporte des erreurs";
    private static final String PASSWORD_ERREUR = "Le mot de passe doit contenir au moins 8 caractères, 1 majuscule, 1 minuscule, 1 chiffre et un caractère spécial (@#$%^&+=!).";
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/user/list")
    public String home(Model model)
    {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/add")
    public String addUser(User user) {
        return "user/add";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
        	model.addAttribute("error",USER_ERREUR);
        	return "redirect:/user/list"; // si il y a une erreur on ne fait pas la suite 
        }
        logger.info("addUser"+ user.toString());
        try {
        User userResultat = userService.addUser(user);
        model.addAttribute("user", userResultat);
        }catch(Exception e) {
        	model.addAttribute("error",PASSWORD_ERREUR);
            return "/user/add";
        }  
        return "redirect:/user/list";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<User> user = userService.getUserById(id);
        logger.info("update user"+ user.toString());
        if(!user.isPresent()) {
        	throw new IllegalArgumentException(USER_ERREUR);
        }
        model.addAttribute("user", user.get());
        return "user/update";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
        	model.addAttribute("error",USER_ERREUR);
        	return "redirect:/user/list"; // si il y a une erreur on ne fait pas la suite 
        }

        logger.info("updateUser"+ user.toString());
        try {
	    	User userResultat = userService.updateUser(user);
	    	model.addAttribute("user", userResultat);
        }catch(Exception e){
        	model.addAttribute("error",PASSWORD_ERREUR);
        	 return "/user/update";
        }
        return "redirect:/user/list";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id) {
    	logger.info("Delete user with id: {}", id);
    	User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("controller.user.notfound" + id));
    	if(id==null){
    		// on remonte une exception
    		throw new IllegalArgumentException("le user n'existe pas");
    	}
    	userService.deleteUserById(id);
        return "redirect:/user/list";
    }
}
