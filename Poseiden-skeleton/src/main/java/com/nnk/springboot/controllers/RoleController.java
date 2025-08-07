package com.nnk.springboot.controllers;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
/**
 * classe qui permet d'indiquer que si le role d'un utilisateur est ADMIN, alors la sécurité authorise 
 * l'accès à certaines fonctionnalités qui ne sont pas visible d'un USER classique 
 */
@ControllerAdvice // annotation qui permet de gérer les exceptions
public class RoleController {

	/**
	 * méthode qui renvoie vrai ou faux. 
	 * permet de savoir si l'utilisateur à un role ADMIN ou un role USER. 
	 * le boolean de cette méthode "isAdmin" permet derrière d'autoriser ou non l'utilisation et la visibilité de certaines fonctionnalités 
	 * @return
	 */
    @ModelAttribute("isAdmin")
    public boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
    }

}
