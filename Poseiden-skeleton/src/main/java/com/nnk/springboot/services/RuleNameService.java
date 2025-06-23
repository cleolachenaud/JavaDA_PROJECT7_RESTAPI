package com.nnk.springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.repositories.RuleNameRepository;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class RuleNameService {
	private static final Logger logger = LogManager.getLogger("RuleNameService");

	@Autowired
	private RuleNameRepository ruleNameRepository;
	
	
	public Iterable<RuleName> getRuleNameList() {
        return ruleNameRepository.findAll();
    }
	
	public RuleName addRuleName(RuleName ruleName) {		
        return ruleNameRepository.save(ruleName);
    }
	
	public RuleName updateRuleName (RuleName ruleName) {
		 // je vérifie que le ruleName existe, si il n'existe pas je remonte une exception
       if(!ruleNameRepository.existsById(ruleName.getId())){
    	   logger.error("ruleName inconnu");
    	   throw new RuntimeException("ruleName n'existe pas en base de données ");
       }
       // si il existe, j'insère le nouveau ruleName en bdd. 
		return ruleNameRepository.save(ruleName);
	}
	
	public Optional<RuleName> getRuleNameById(Integer id) {
        return ruleNameRepository.findById(id);
    }
	public void deleteRuleNameById(Integer id) {
		 // je vérifie que le ruleName existe
		ruleNameRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("ruleName inconnu en base"));
		ruleNameRepository.deleteById(id);
    }

}
