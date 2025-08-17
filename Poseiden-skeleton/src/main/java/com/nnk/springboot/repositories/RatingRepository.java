package com.nnk.springboot.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nnk.springboot.domain.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {
	Optional<Rating> findById(Rating id);
	
	boolean existsById(Integer id);
}
