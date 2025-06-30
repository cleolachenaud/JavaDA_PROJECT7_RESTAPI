package com.nnk.springboot;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*; 
import static org.mockito.Mockito.*; 
 
import org.springframework.ui.Model; 
import org.springframework.validation.BindingResult; 
import org.springframework.validation.BeanPropertyBindingResult; 
import org.springframework.boot.test.context.SpringBootTest;


import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
@SpringBootTest
public class CurvePointControllerTest {
	

	@Mock
    private CurvePointService curvePointService; 
    @Mock
    private Model model; 
    @InjectMocks
    private CurveController curveController;

    public CurvePointControllerTest() {
        MockitoAnnotations.openMocks(this); 
         
    }

   

    @Test
    public void testAddBidForm() {

        CurvePoint curvePoint = new CurvePoint(); 
        String viewName = curveController.addCurveForm(curvePoint); 
        assertEquals("curvePoint/add", viewName); 
    }
    @Test
    public void testValidateAvecErreurs() {

        CurvePoint curvePoint = new CurvePoint(); 
        BindingResult result = new BeanPropertyBindingResult(curvePoint, "curvePoint");
        result.reject("error", "controller.curvepoint.erreur"); // Simuler une erreur

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        	curveController.validate(curvePoint, result, model);
        });
        verifyNoInteractions(curvePointService); // Je vérifie que le service n'a pas été appelé
        assertEquals("controller.curvepoint.erreur", exception.getMessage()); // Je vérifie le message d'exception
        
    }

    @Test
    public void testValidateSansErreurs() {

        CurvePoint curvePoint = new CurvePoint(10, 10d, 30d); 
        BindingResult result = new BeanPropertyBindingResult(curvePoint, "curvePoint");

        when(curvePointService.addCurvePoint(curvePoint)).thenReturn(curvePoint); 

        String viewName = curveController.validate(curvePoint, result, model); 
     
        verify(model).addAttribute("curvePoint", curvePoint); // Je vérifie l'ajout au modèle
        verify(curvePointService).addCurvePoint(curvePoint); // Je vérifie que le service a été appelé
        assertEquals("curvePoint/add", viewName); // Je vérifie que la vue retournée est correcte
    }
}
