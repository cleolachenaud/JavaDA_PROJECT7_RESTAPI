package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface BidListRepository extends JpaRepository<BidList, Integer> {

	Optional<BidList> findByBidListId(BidList bidListId);
	
	boolean existsById(Integer id);
}
