package com.nnk.springboot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nnk.springboot.domain.Trade;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {
	

	Optional<Trade> findByTradeId(Trade trade);
	
	boolean existsById(Integer id);
}
