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


import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
@SpringBootTest
public class TradeControllerTest {
	

	@Mock
    private TradeService tradeService; 
    @Mock
    private Model model; 
    @InjectMocks
    private TradeController tradeController;

    public TradeControllerTest() {
        MockitoAnnotations.openMocks(this); 
         
    }

   

    @Test
    public void testAddTradeForm() {

    	Trade trade = new Trade(); 
        String viewName = tradeController.addTradeForm(trade); 
        assertEquals("trade/add", viewName); 
    }
    @Test
    public void testValidateAvecErreurs() {

    	Trade trade = new Trade(); 
        BindingResult result = new BeanPropertyBindingResult(trade, "trade");
        result.reject("error", "controller.trade.erreur"); // Simuler une erreur

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        	tradeController.validate(trade, result, model);
        });
        verifyNoInteractions(tradeService); // Je vérifie que le service n'a pas été appelé
        assertEquals("controller.trade.erreur", exception.getMessage()); // Je vérifie le message d'exception
        
    }

    @Test
    public void testValidateSansErreurs() {

    	Trade trade = new Trade("Trade Account", "Type"); 
        BindingResult result = new BeanPropertyBindingResult(trade, "trade");

        when(tradeService.addTrade(trade)).thenReturn(trade); 

        String viewName = tradeController.validate(trade, result, model); 
     
        verify(model).addAttribute("trade", trade); // Je vérifie l'ajout au modèle
        verify(tradeService).addTrade(trade); // Je vérifie que le service a été appelé
        assertEquals("trade/add", viewName); // Je vérifie que la vue retournée est correcte
    }
    @Test
    public void testValidateUpdateSansErreurs() {

    	Trade trade = new Trade("Trade Account", "Type");
        BindingResult result = new BeanPropertyBindingResult(trade, "trade");

        when(tradeService.updateTrade(trade)).thenReturn(trade);

        String viewName = tradeController.updateTrade(null, trade, result, model); 
     
        verify(model).addAttribute("trade", trade); // Je vérifie l'ajout au modèle
        verify(tradeService).updateTrade(trade); // Je vérifie que le service a été appelé
        assertEquals("redirect:/trade/list", viewName); // Je vérifie que la vue retournée est correcte
    }
    
    @Test
    public void testDeleteSansErreurs() {
    	Integer id = 1;

        doNothing().when(tradeService).deleteTradeById(id);
        String viewName = tradeController.deleteTrade(id);
        verify(tradeService).deleteTradeById(id); // Je vérifie que le service a été appelé
        assertEquals("redirect:/trade/list", viewName); // Je vérifie que la vue retournée est correcte
    }
}
