package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
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

@Controller
public class RatingController {
	
	private static final Logger logger = LogManager.getLogger("RatingController");
	@Autowired
	RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
    	Iterable<Rating> ratingList = ratingService.getRatingList();
    	logger.info("ratingList"+ ratingList.toString());
    	model.addAttribute("liste de tous les rating",ratingList);
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
    	if(result.hasErrors()){
    		// on remonte une exception
    		throw new IllegalArgumentException("controller.rating.erreur");
    	}
    	// si le bindingResult ne contient pas d'erreur, on effectue le traitement
		logger.info("addRating"+ rating.toString());
		Rating ratingResultat = ratingService.addRating(rating);
		model.addAttribute("rating", ratingResultat);
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	Optional<Rating> rating = ratingService.getRatingById(id);
    	logger.info("updateRatingShowForm"+ rating.toString());
    	model.addAttribute(rating);
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
    	if(result.hasErrors()){
    		// on remonte une exception
    		throw new IllegalArgumentException("controller.rating.erreur");
    	}
    	// si le bindingResult ne contient pas d'erreur, on effectue le traitement
		logger.info("updateRating"+ rating.toString());
		Rating ratingResultat = ratingService.updateRating(rating);
		model.addAttribute("rating", ratingResultat);
        return "redirect:/rating/list";
    }
    @GetMapping("/rating/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
    	Optional<Rating> rating = ratingService.getRatingById(id);
    	logger.info("delete rating");
    	model.addAttribute(rating);
        return "rating/delete";
    }
    
    @PostMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id) {
    	logger.info("Delete rating with id: {}", id);
    	ratingService.deleteRatingById(id);
        return "redirect:/rating/list";
    }
}
