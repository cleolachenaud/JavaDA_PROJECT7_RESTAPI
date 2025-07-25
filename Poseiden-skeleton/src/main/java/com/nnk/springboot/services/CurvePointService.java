package com.nnk.springboot.services;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.configuration.ConstantesUtils;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;


@Service
public class CurvePointService {
	
	private static final Logger logger = LogManager.getLogger("CurvePointService");

	@Autowired
	private CurvePointRepository curvePointRepository;
	
	
	public Iterable<CurvePoint> getCurvePointList() {
        return curvePointRepository.findAll();
    }
	
	public CurvePoint addCurvePoint(CurvePoint curvePoint) {		
        return curvePointRepository.save(curvePoint);
    }
	
	public CurvePoint updateCurvePoint (CurvePoint curvePoint) {
		 // je vérifie que le curvePoint existe, si il n'existe pas je remonte une exception
       if(!curvePointRepository.existsById(curvePoint.getId())){
    	   logger.error(ConstantesUtils.CURVEPOINT_UNKNOWN);
    	   throw new RuntimeException(ConstantesUtils.CURVEPOINT_NOTFOUND);
       }
       // si il existe, j'insère le nouveau curvePoint en bdd. 
		return curvePointRepository.save(curvePoint);
	}
	
	public Optional<CurvePoint> getCurvePointById(Integer id) {
        return curvePointRepository.findById(id);
    }
	public void deleteCurvePointById(Integer id) {
        curvePointRepository.findById(id)
			.orElseThrow(() -> new RuntimeException(ConstantesUtils.CURVEPOINT_NOTFOUND));
        curvePointRepository.deleteById(id);
    }
}
