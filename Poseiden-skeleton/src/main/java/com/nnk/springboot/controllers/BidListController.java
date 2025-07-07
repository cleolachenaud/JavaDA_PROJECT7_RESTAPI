package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.BidListService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import javax.validation.Valid;


@Controller
public class BidListController {
	private static final String BIDLIST_ERREUR = "le bidlist comporte des erreurs";
	private static final Logger logger = LogManager.getLogger("BidListController");
    
	@Autowired
	BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
    	Iterable<BidList> bidListList = bidListService.getBidListList();
    	logger.info("bidListList"+ bidListList.toString());
    	model.addAttribute("bidLists",bidListList);
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model) {
    	if(result.hasErrors()){
    		// on remonte une exception
    		throw new IllegalArgumentException(BIDLIST_ERREUR);
    	}
    	// si le bindingResult ne contient pas d'erreur, on effectue le traitement
		logger.info("addBidList"+ bidList.toString());
		BidList bidListResultat = bidListService.addBidList(bidList);
		model.addAttribute("bidList", bidListResultat);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer bidListId, Model model) {
    	Optional<BidList> bidList = bidListService.getBidListById(bidListId);
    	logger.info("updateBidList"+ bidList.toString());
    	if(!bidList.isPresent()){
    		// on remonte une exception
    		throw new IllegalArgumentException(BIDLIST_ERREUR);
    	}
    	model.addAttribute(bidList.get());// je récupère mon objet qui est dans mon Optional
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer bidListId, @Valid BidList bidList,
                             BindingResult result, Model model) {
       	if(result.hasErrors()){
    		// on remonte une exception
    		throw new IllegalArgumentException(BIDLIST_ERREUR);
    	}
    	// si le bindingResult ne contient pas d'erreur, on effectue le traitement
		logger.info("updateBidList"+ bidList.toString());
		BidList bidListResultat = bidListService.updateBidList(bidList);
		model.addAttribute("bidList", bidListResultat);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
    	logger.info("Delete bid with id: {}", id);
        if(id==null){
    		// on remonte une exception
    		throw new IllegalArgumentException(BIDLIST_ERREUR);
    	}
        bidListService.deleteBidListById(id);
        return "redirect:/bidList/list";
        
    }
    /*TODO Optional<BidList> bidList = bidListService.getBidListById(id);
    	logger.info("delete bidlist");
    	model.addAttribute(bidList);
        return "bidList/delete";
    }
    
    @PostMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id) {
        logger.info("Delete bid with id: {}", id);
        bidListService.deleteBidListById(id);
        return "redirect:/bidList/list";
    }*/
}
