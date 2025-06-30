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


import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
@SpringBootTest
public class RuleControllerTest {
	

	@Mock
    private RuleNameService ruleNameService; 
    @Mock
    private Model model; 
    @InjectMocks
    private RuleNameController ruleNameController;

    public RuleControllerTest() {
        MockitoAnnotations.openMocks(this); 
         
    }

   

    @Test
    public void testAddBidForm() {

    	RuleName ruleName = new RuleName(); 
        String viewName = ruleNameController.addRuleForm(ruleName); 
        assertEquals("ruleName/add", viewName); 
    }
    @Test
    public void testValidateAvecErreurs() {

    	RuleName ruleName = new RuleName(); 
        BindingResult result = new BeanPropertyBindingResult(ruleName, "ruleName");
        result.reject("error", "controller.ruleName.erreur"); // Simuler une erreur

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        	ruleNameController.validate(ruleName, result, model);
        });
        verifyNoInteractions(ruleNameService); // Je vérifie que le service n'a pas été appelé
        assertEquals("controller.rulename.erreur", exception.getMessage()); // Je vérifie le message d'exception
        
    }

    @Test
    public void testValidateSansErreurs() {

    	RuleName ruleName = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        BindingResult result = new BeanPropertyBindingResult(ruleName, "curvePoint");

        when(ruleNameService.addRuleName(ruleName)).thenReturn(ruleName); 

        String viewName = ruleNameController.validate(ruleName, result, model); 
     
        verify(model).addAttribute("ruleName", ruleName); // Je vérifie l'ajout au modèle
        verify(ruleNameService).addRuleName(ruleName); // Je vérifie que le service a été appelé
        assertEquals("ruleName/add", viewName); // Je vérifie que la vue retournée est correcte
    }
}
