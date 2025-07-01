package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.CurvePointService;
import com.nnk.springboot.services.RuleNameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import javax.validation.Valid;

@Controller
public class RuleNameController {
	private static final Logger logger = LogManager.getLogger("RuleNameController");
	
    // Constructeur pour injecter le service
    public RuleNameController(RuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }
	
	@Autowired
	RuleNameService ruleNameService;

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
    	Iterable<RuleName> ruleNameList = ruleNameService.getRuleNameList();
    	logger.info("ruleNameList"+ ruleNameList.toString());
    	model.addAttribute("liste de tous les rule name",ruleNameList);
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
    	if(result.hasErrors()){
    		// on remonte une exception
    		throw new IllegalArgumentException("controller.rulename.erreur");
    	}
    	// si le bindingResult ne contient pas d'erreur, on effectue le traitement
		logger.info("addRuleName"+ ruleName.toString());
    	RuleName ruleNameResultat = ruleNameService.addRuleName(ruleName);
		model.addAttribute("ruleName", ruleNameResultat);
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	Optional<RuleName> ruleName = ruleNameService.getRuleNameById(id);
    	logger.info("updateRuleName"+ ruleName.toString());
    	model.addAttribute(ruleName);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
    	if(result.hasErrors()){
    		// on remonte une exception
    		throw new IllegalArgumentException("controller.rulename.erreur");
    	}
    	// si le bindingResult ne contient pas d'erreur, on effectue le traitement
		logger.info("updateCurvePoint"+ ruleName.toString());
		RuleName ruleNameResultat = ruleNameService.updateRuleName(ruleName);
		model.addAttribute("ruleName", ruleNameResultat);
        return "redirect:/ruleName/list";
    }
    @GetMapping("/ruleName/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
    	Optional<RuleName> ruleName = ruleNameService.getRuleNameById(id);
    	logger.info("delete ruleName");
    	model.addAttribute(ruleName);
        return "ruleName/delete";
    }
    @PostMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id) {
    	logger.info("Delete rulename with id: {}", id);
    	ruleNameService.deleteRuleNameById(id);
        return "redirect:/ruleName/list";
    }
}
