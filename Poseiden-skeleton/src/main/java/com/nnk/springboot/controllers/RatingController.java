package com.nnk.springboot.controllers;

import com.nnk.springboot.configuration.ConstantesUtils;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import javax.validation.Valid;
/**
 * classe de controller pour rating
 */
@Controller
public class RatingController {
	
	private static final Logger logger = LogManager.getLogger("RatingController");
	@Autowired
	RatingService ratingService;
/**
 * retourne la liste des rating
 * @param model
 * @return
 */
    @RequestMapping("/rating/list")
    public String home(Model model)
    {
    	Iterable<Rating> ratingList = ratingService.getRatingList();
    	logger.info("ratingList"+ ratingList.toString());
    	model.addAttribute("ratings",ratingList);
        return "rating/list";
    }
/**
 * méthode de saisie du formulaire d'ajout
 * @param rating
 * @return
 */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }
/**
 * méthode de validation du formulaire d'ajout
 * @param rating
 * @param result
 * @param model
 * @return
 */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
    	if(result.hasErrors()){
    		// on remonte une exception
    		throw new IllegalArgumentException(ConstantesUtils.RATING_ERREUR);
    	}
    	// si le bindingResult ne contient pas d'erreur, on effectue le traitement
		logger.info("addRating"+ rating.toString());
		Rating ratingResultat = ratingService.addRating(rating);
		model.addAttribute("rating", ratingResultat);
        return "redirect:/rating/list";
    }
/**
 * méthode de saisie du formulaire de mise à jour
 * @param id
 * @param model
 * @return
 */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	Optional<Rating> rating = ratingService.getRatingById(id);
    	logger.info("updateRatingShowForm"+ rating.toString());
    	if(!rating.isPresent()) {
    	    throw new IllegalArgumentException(ConstantesUtils.RATING_ERREUR);
    	}
    	model.addAttribute("rating", rating.get());// je récupère mon objet qui est dans mon Optional
        return "rating/update";
    }
/**
 * méthode de validation du formulaire de mise à jour 
 * @param id
 * @param rating
 * @param result
 * @param model
 * @return
 */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
    	if(result.hasErrors()){
    		// on remonte une exception
    		throw new IllegalArgumentException(ConstantesUtils.RATING_ERREUR);
    	}
    	// si le bindingResult ne contient pas d'erreur, on effectue le traitement
		logger.info("updateRating"+ rating.toString());
		Rating ratingResultat = ratingService.updateRating(rating);
		model.addAttribute("rating", ratingResultat);
        return "redirect:/rating/list";
    }
   /**
    * gère la suppression 
    * @param id
    * @param model
    * @return
    */
    @GetMapping("/rating/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
    	logger.info("Delete rating with id: {}", id);
    	if(id==null){
    		// on remonte une exception
    		throw new IllegalArgumentException(ConstantesUtils.RATING_ERREUR);
    	}
    	ratingService.deleteRatingById(id);
        return "redirect:/rating/list";
    }
}
