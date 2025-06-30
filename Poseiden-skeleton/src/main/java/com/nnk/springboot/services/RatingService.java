package com.nnk.springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

@Service
public class RatingService {
	
	private static final Logger logger = LogManager.getLogger("RatingService");

	@Autowired
	private RatingRepository ratingRepository;
	
	public Iterable<Rating> getRatingList() {
        return ratingRepository.findAll();
    }
	
	public Rating addRating(Rating rating) {		
        return ratingRepository.save(rating);
    }
	
	public Rating updateRating (Rating rating) {
		 // je vérifie que le rating existe, si il n'existe pas je remonte une exception
      if(!ratingRepository.existsById(rating.getId())){
   	   logger.error("rating inconnu");
   	   throw new RuntimeException("service.rating.notfound");
      }
      // si il existe, j'insère le nouveau rating en bdd. 
		return ratingRepository.save(rating);
	}
	
	public Optional<Rating> getRatingById(Integer id) {
        return ratingRepository.findById(id);
    }
	public void deleteRatingById(Integer id) {
		 // je vérifie que le rating existe
		ratingRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("service.rating.notfound"));
		ratingRepository.deleteById(id);
   }
}
