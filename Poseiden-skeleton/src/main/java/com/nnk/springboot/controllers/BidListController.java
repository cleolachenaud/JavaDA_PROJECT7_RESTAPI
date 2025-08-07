package com.nnk.springboot.controllers;

import com.nnk.springboot.configuration.ConstantesUtils;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import javax.validation.Valid;

/**
 * classe de controller pour les bidlist. 
 */
@Controller
public class BidListController {

	private static final Logger logger = LogManager.getLogger("BidListController");
    
	@Autowired
	BidListService bidListService;

	/**
	 * page d'accueil, retourne la liste des bidlist
	 * @param model
	 * @return
	 */
    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
    	Iterable<BidList> bidListList = bidListService.getBidListList(); // on fait une liste de tous les bidlists déjà présent
    	logger.info("bidListList"+ bidListList.toString());
    	model.addAttribute("bidLists",bidListList);
        return "bidList/list";
    }
/**
 * méthode saisie du formulaire d'ajout 
 * @param bid
 * @return
 */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }
/**
 * méthode de validation du formulaire d'ajout 
 *  
 * 
 * @param bidList
 * @param result
 * @param model
 * @return
 */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model) {
    	if(result.hasErrors()){
    		// si le résultat comporte des erreurs on remonte une exception
    		throw new IllegalArgumentException(ConstantesUtils.BIDLIST_ERREUR);
    	}
    	// si le bindingResult ne contient pas d'erreur, on effectue le traitement
		logger.info("addBidList"+ bidList.toString());
		BidList bidListResultat = bidListService.addBidList(bidList);
		model.addAttribute("bidList", bidListResultat);
        return "redirect:/bidList/list"; // et on retourne sur la page bidlistlist
    }

    /**
     * méthode de saisie du formulaire de mise à jour 
     * @param bidListId
     * @param model
     * @return
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer bidListId, Model model) {
    	Optional<BidList> bidList = bidListService.getBidListById(bidListId);
    	logger.info("updateBidList"+ bidList.toString());
    	if(!bidList.isPresent()){
    		// on remonte une exception si le bidlist est en erreur 
    		throw new IllegalArgumentException(ConstantesUtils.BIDLIST_ERREUR);
    	}
    	model.addAttribute(bidList.get());// je récupère mon objet qui est dans mon Optional
        return "bidList/update"; // et je renvoie vers la validation du formulaire
    }
/**
 * methode de validation du formulaire de mise à jour
 * @param bidListId
 * @param bidList
 * @param result
 * @param model
 * @return
 */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer bidListId, @Valid BidList bidList,
                             BindingResult result, Model model) {
       	if(result.hasErrors()){
    		// on remonte une exception si le resultat comporte une erreur 
    		throw new IllegalArgumentException(ConstantesUtils.BIDLIST_ERREUR);
    	}
    	// si le bindingResult ne contient pas d'erreur, on effectue le traitement
		logger.info("updateBidList"+ bidList.toString());
		BidList bidListResultat = bidListService.updateBidList(bidList);
		model.addAttribute("bidList", bidListResultat);
        return "redirect:/bidList/list";
    }
/**
 * controller qui gère la suppression 
 * @param id
 * @param model
 * @return
 */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
    	logger.info("Delete bid with id: {}", id);
        if(id==null){
    		// on remonte une exception si l'ID est null 
    		throw new IllegalArgumentException(ConstantesUtils.BIDLIST_ERREUR);
    	}
        bidListService.deleteBidListById(id);
        return "redirect:/bidList/list";
        
    }

}
