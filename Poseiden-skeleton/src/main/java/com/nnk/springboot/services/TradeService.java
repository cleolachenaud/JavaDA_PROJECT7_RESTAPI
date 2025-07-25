package com.nnk.springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.configuration.ConstantesUtils;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class TradeService {
	
	private static final Logger logger = LogManager.getLogger("TradeService");

	@Autowired
	private TradeRepository tradeRepository;
	
	
	public Iterable<Trade> getTradeList() {
        return tradeRepository.findAll();
    }
	
	public Trade addTrade(Trade trade) {		
        return tradeRepository.save(trade);
    }
	
	public Trade updateTrade (Trade trade) {
		 // je vérifie que le trade existe, si il n'existe pas je remonte une exception
       if(!tradeRepository.existsById(trade.getTradeId())){
    	   logger.error(ConstantesUtils.TRADE_UNKNOWN);
    	   throw new RuntimeException(ConstantesUtils.TRADE_NOTFOUND);
       }
       // si il existe, j'insère le nouveau trade en bdd. 
		return tradeRepository.save(trade);
	}
	
	public Optional<Trade> getTradeById(Integer id) {
        return tradeRepository.findById(id);
    }
	public void deleteTradeById(Integer id) {
		 // je vérifie que le trade existe
		tradeRepository.findById(id)
			.orElseThrow(() -> new RuntimeException(ConstantesUtils.TRADE_NOTFOUND));
		tradeRepository.deleteById(id);
    }

}
