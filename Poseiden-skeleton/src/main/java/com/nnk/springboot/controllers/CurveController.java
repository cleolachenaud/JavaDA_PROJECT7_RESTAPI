package com.nnk.springboot.controllers;


import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
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
public class CurveController {
	private static final String CURVEPOINT_ERREUR = "le curvePoint comporte des erreurs";
	private static final Logger logger = LogManager.getLogger("CurvePointController");
	
    // Constructeur pour injecter le service
    public CurveController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }
	
	@Autowired
	CurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
    	Iterable<CurvePoint> curvePointList = curvePointService.getCurvePointList();
    	logger.info("curvePointList"+ curvePointList.toString());
    	model.addAttribute("curvePoints",curvePointList);
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurveForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
    	if(result.hasErrors()){
    		// on remonte une exception
    		throw new IllegalArgumentException(CURVEPOINT_ERREUR);
    	}
    	// si le bindingResult ne contient pas d'erreur, on effectue le traitement
		logger.info("addCurvePoint"+ curvePoint.toString());
    	CurvePoint curvePointResultat = curvePointService.addCurvePoint(curvePoint);
		model.addAttribute("curvePoint", curvePointResultat);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	Optional<CurvePoint> curvePoint = curvePointService.getCurvePointById(id);
    	logger.info("updateCurvePoint"+ curvePoint.toString());
    	if(!curvePoint.isPresent()){
    		// on remonte une exception
    		throw new IllegalArgumentException(CURVEPOINT_ERREUR);
    	}
    	model.addAttribute("curvePoint", curvePoint.get()); // je récupère mon objet qui est dans mon Optional
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
    	if(result.hasErrors()){
    		// on remonte une exception
    		throw new IllegalArgumentException(CURVEPOINT_ERREUR);
    	}
    	// si le bindingResult ne contient pas d'erreur, on effectue le traitement
		logger.info("updateCurvePoint"+ curvePoint.toString());
    	CurvePoint curvePointResultat = curvePointService.updateCurvePoint(curvePoint);
		model.addAttribute("curvePoint", curvePointResultat);
        return "redirect:/curvePoint/list";
    }
    
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
    	logger.info("delete curvePoint with id: {}", id);
    	if(id==null){
    		// on remonte une exception
    		throw new IllegalArgumentException(CURVEPOINT_ERREUR);
    	}
    	curvePointService.deleteCurvePointById(id);
        return "redirect:/curvePoint/list";
    	/*TODO Optional<CurvePoint> curvePoint = curvePointService.getCurvePointById(id);
    	logger.info("delete curvePoint");
    	if(curvePoint.get()==null){
    		// on remonte une exception
    		throw new IllegalArgumentException("controller.curvepoint.erreur");
    	}
    	model.addAttribute(curvePoint.get());
        return "curvePoint/delete";*/
    }
/*
    @PostMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id) {
    	logger.info("delete curvePoint with id: {}", id);
    	curvePointService.deleteCurvePointById(id);
        return "redirect:/curvePoint/list";
    }*/
}
