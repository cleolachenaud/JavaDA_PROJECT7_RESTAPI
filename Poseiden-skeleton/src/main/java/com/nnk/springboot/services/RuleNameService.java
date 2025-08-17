package com.nnk.springboot.services;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.configuration.ConstantesUtils;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
/**
 * classe du service de l'identité de RULENAME permet d'appeler les méthodes de création, modification, suppression
 */
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
    	   logger.error(ConstantesUtils.RULENAME_UNKNOWN);
    	   throw new RuntimeException(ConstantesUtils.RULENAME_NOTFOUND);
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
			.orElseThrow(() -> new RuntimeException(ConstantesUtils.RULENAME_NOTFOUND));
		ruleNameRepository.deleteById(id);
    }

}
