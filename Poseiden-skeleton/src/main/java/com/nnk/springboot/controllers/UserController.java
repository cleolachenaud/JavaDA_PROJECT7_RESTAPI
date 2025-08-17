package com.nnk.springboot.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.configuration.ConstantesUtils;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
/** 
 * classe qui gere le controller de user
 * attention, cette fonctionnalité n'est autorisée que pour les ADMIN
 * l'annotation @PreAuthorize filtre les roles des utilisateurs. 
 */
@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
	@Autowired
	UserService userService;
    
    private static final Logger logger = LogManager.getLogger("UserController");
    /**
     * affiche la liste des utilisateurs déjà enregistrés en bdd
     * @param model
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')") // annotation qui demande que le role soit ADMIN pour pouvoir accéder à la méthode
    @RequestMapping("/user/list")
    public String home(Model model)
    {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }
    /**
     * méthode saisie du formulaire d'ajout
     * @param user
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/add")
    public String addUser(User user) {
        return "user/add";
    }
    /**
     * validation du formulaire d'ajout
     * @param user
     * @param result
     * @param model
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
        	model.addAttribute("error",ConstantesUtils.USER_ERREUR);
        	return "/user/add"; // si il y a une erreur on ne fait pas la suite 
        }
        logger.info("addUser"+ user.toString());
        
        try {
        User userResultat = userService.addUser(user);
        model.addAttribute("user", userResultat);
        }catch(Exception e) {
        	model.addAttribute("error",e.getMessage());// il peut y avoir plusieurs types d'exceptions remontées par le service. 
            return "/user/add";
        }  
        return "redirect:/user/list";
    }
    /**
     * saisie du formulaire de mise à jour
     * @param id
     * @param model
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<User> user = userService.getUserById(id);
        logger.info("update user"+ user.toString());
        if(!user.isPresent()) {
        	throw new IllegalArgumentException(ConstantesUtils.USER_NOTFOUND);
        }
        model.addAttribute("user", user.get());
        return "user/update";
    }
    /**
     * validation du formulaire de mise à jour
     * @param id
     * @param user
     * @param result
     * @param model
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
        	model.addAttribute("error",ConstantesUtils.USER_ERREUR);
        	return "/user/update"; // si il y a une erreur on ne fait pas la suite 
        }

        logger.info("updateUser"+ user.toString());
        try {
	    	User userResultat = userService.updateUser(user);
	    	model.addAttribute("user", userResultat);
        }catch(Exception e){
        	model.addAttribute("error",e.getMessage());
        	 return "/user/update";
        }
        return "redirect:/user/list";
    }
    /** 
     * suppression d'un utilisateur 
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id) {
    	logger.info("Delete user with id: {}", id);
    	// si l'ID est null ou non présent en base
    	userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(ConstantesUtils.USER_NOTFOUND + id));
    	userService.deleteUserById(id);
        return "redirect:/user/list";
    }
}
