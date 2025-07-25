package com.nnk.springboot.services;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.configuration.ConstantesUtils;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@Service
public class BidListService {
	
	private static final Logger logger = LogManager.getLogger("BidListService");

	
	@Autowired
	private BidListRepository bidListRepository;
	
	
	public Iterable<BidList> getBidListList() {
        return bidListRepository.findAll();
    }
	
	public BidList addBidList(BidList bidList) {		
        return bidListRepository.save(bidList);
    }
	
	public BidList updateBidList (BidList bidList) {
		 // je vérifie que le BidList existe, si il n'existe pas je remonte une exception
       if(!bidListRepository.existsById(bidList.getBidListId())){
    	   logger.error(ConstantesUtils.BIDLIST_UNKNOWN);
    	   throw new RuntimeException( ConstantesUtils.BIDLIST_ERREUR);
       }
       // si il existe, j'insère le nouveau BidList en bdd. 
		return bidListRepository.save(bidList);
	}
	
	public Optional<BidList> getBidListById(Integer id) {
        return bidListRepository.findById(id);
    }
	public void deleteBidListById(Integer id) {
		 // je vérifie que le bidlist existe
		bidListRepository.findById(id)
			.orElseThrow(() -> new RuntimeException(ConstantesUtils.BIDLIST_ERREUR));
		bidListRepository.deleteById(id);
    }
}
