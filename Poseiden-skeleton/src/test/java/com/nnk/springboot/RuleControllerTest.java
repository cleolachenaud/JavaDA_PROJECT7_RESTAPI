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
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
@SpringBootTest
public class RuleControllerTest {
	
	private static final String RULENAME_ERREUR = "le ruleName comporte des erreurs";
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
        result.reject("error", RULENAME_ERREUR); // Simuler une erreur

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        	ruleNameController.validate(ruleName, result, model);
        });
        verifyNoInteractions(ruleNameService); // Je vérifie que le service n'a pas été appelé
        assertEquals(RULENAME_ERREUR, exception.getMessage()); // Je vérifie le message d'exception
        
    }

    @Test
    public void testValidateSansErreurs() {

    	RuleName ruleName = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        BindingResult result = new BeanPropertyBindingResult(ruleName, "curvePoint");

        when(ruleNameService.addRuleName(ruleName)).thenReturn(ruleName); 

        String viewName = ruleNameController.validate(ruleName, result, model); 
     
        verify(model).addAttribute("ruleName", ruleName); // Je vérifie l'ajout au modèle
        verify(ruleNameService).addRuleName(ruleName); // Je vérifie que le service a été appelé
        assertEquals("redirect:/ruleName/list", viewName); // Je vérifie que la vue retournée est correcte
    }
    
    @Test
    public void testUpdateSansErreurs() {

    	RuleName ruleName = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        BindingResult result = new BeanPropertyBindingResult(ruleName, "ruleName");

        when(ruleNameService.updateRuleName(ruleName)).thenReturn(ruleName);

        String viewName = ruleNameController.updateRuleName(null, ruleName, result, model); 
     
        verify(model).addAttribute("ruleName", ruleName); // Je vérifie l'ajout au modèle
        verify(ruleNameService).updateRuleName(ruleName); // Je vérifie que le service a été appelé
        assertEquals("redirect:/ruleName/list", viewName); // Je vérifie que la vue retournée est correcte
    }
    
    @Test
    public void testDeleteSansErreurs() {
    	Integer id = 1;

        doNothing().when(ruleNameService).deleteRuleNameById(id);
        String viewName = ruleNameController.deleteBid(id, model);
        verify(ruleNameService).deleteRuleNameById(id); // Je vérifie que le service a été appelé
        assertEquals("redirect:/ruleName/list", viewName); // Je vérifie que la vue retournée est correcte
    }
}
