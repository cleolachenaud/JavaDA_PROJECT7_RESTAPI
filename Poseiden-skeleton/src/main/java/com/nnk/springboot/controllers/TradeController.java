package com.nnk.springboot.controllers;

import com.nnk.springboot.configuration.ConstantesUtils;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;

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
/**
 * controller qui gere les  trade
 */
@Controller
public class TradeController {
private static final Logger logger = LogManager.getLogger("TradeController");

	@Autowired
	TradeService tradeService;
/**
 * page d'accueil retourne la liste des trade
 * @param model
 * @return
 */
    @RequestMapping("/trade/list")
    public String home(Model model)
    {
    	Iterable<Trade> tradeList = tradeService.getTradeList();
    	logger.info("tradeList"+ tradeList.toString());
    	model.addAttribute("trades",tradeList);
        return "trade/list";
    }
/**
 * méthode de saisie du formulaire d'ajout
 * @param trade
 * @return
 */
    @GetMapping("/trade/add")
    public String addTradeForm(Trade trade) {
        return "trade/add";
    }
/**
 * méthode de validation du formulaire
 * @param trade
 * @param result
 * @param model
 * @return
 */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
    	if(result.hasErrors()){
    		// on remonte une exception
    		throw new IllegalArgumentException(ConstantesUtils.TRADE_ERREUR);
    	}
    	// si le bindingResult ne contient pas d'erreur, on effectue le traitement
		logger.info("addTrade"+ trade.toString());
    	Trade tradeResultat = tradeService.addTrade(trade);
		model.addAttribute("trade", tradeResultat);
        return "redirect:/trade/list";
    }
/**
 * méthode de saisie du formulaire de mise a jour
 * @param id
 * @param model
 * @return
 */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
       	Optional<Trade> trade = tradeService.getTradeById(id);
    	logger.info("updateTrade"+ trade.toString());
    	if(!trade.isPresent()){
    		// on remonte une exception
    		throw new IllegalArgumentException(ConstantesUtils.TRADE_ERREUR);
    	}
    	model.addAttribute("trade",trade.get());
        return "trade/update";
    }
/**
 * methode de validation du formulaire dé mise à jour
 * @param id
 * @param trade
 * @param result
 * @param model
 * @return
 */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
    	if(result.hasErrors()){
    		// on remonte une exception
    		throw new IllegalArgumentException(ConstantesUtils.TRADE_ERREUR);
    	}
    	// si le bindingResult ne contient pas d'erreur, on effectue le traitement
		logger.info("updateTrade"+ trade.toString());
    	Trade tradeResultat = tradeService.updateTrade(trade);
		model.addAttribute("trade", tradeResultat);
        return "redirect:/trade/list";
    }
    
    /**
     * controller qui gere la suppression
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        logger.info("Delete trade with id: {}", id);
       	if(id==null){
    		// on remonte une exception
    		throw new IllegalArgumentException(ConstantesUtils.TRADE_ERREUR);
    	}
       	tradeService.deleteTradeById(id);
        return "redirect:/trade/list";
    }

}
