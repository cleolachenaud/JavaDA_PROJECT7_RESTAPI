package com.nnk.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
@AutoConfigureMockMvc
@SpringBootTest
public class BidControllerTest {
	private static final String BIDLIST_ERREUR = "le bidlist comporte des erreurs";

	@Mock
    private BidListService bidListService; 
    @Mock
    private Model model; 
    @InjectMocks
    private BidListController bidListController;
    @Autowired
    private MockMvc mockMvc;
    
    
    
    public BidControllerTest() {
        MockitoAnnotations.openMocks(this); 
         
    }

   

    @Test
    public void testAddBidForm() {

        BidList bidList = new BidList(); 
        String viewName = bidListController.addBidForm(bidList); 
        assertEquals("bidList/add", viewName); 
    }
    @Test
    public void testValidateAvecErreurs() {

    	BidList bidList = new BidList();  
        BindingResult result = new BeanPropertyBindingResult(bidList, "bidList");
        result.reject("error", BIDLIST_ERREUR); // Simuler une erreur

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        	bidListController.validate(bidList, result, model);
        });
        verifyNoInteractions(bidListService); // Je vérifie que le service n'a pas été appelé
        assertEquals(BIDLIST_ERREUR, exception.getMessage()); // Je vérifie le message d'exception
        
    }

    @Test
    public void testValidateSansErreurs() {

    	BidList bidList = new BidList("Account Test", "Type Test", 10d);
        BindingResult result = new BeanPropertyBindingResult(bidList, "bidList");

        when(bidListService.addBidList(bidList)).thenReturn(bidList); 

        String viewName = bidListController.validate(bidList, result, model); 
     
        verify(model).addAttribute("bidList", bidList); // Je vérifie l'ajout au modèle
        verify(bidListService).addBidList(bidList); // Je vérifie que le service a été appelé
        assertEquals("redirect:/bidList/list", viewName); // Je vérifie que la vue retournée est correcte
    }
    

    @Test
    public void testValidateUpdateSansErreurs() {

    	BidList bidList = new BidList("Account Test", "Type Test", 10d);
        BindingResult result = new BeanPropertyBindingResult(bidList, "bidList");

        when(bidListService.updateBidList(bidList)).thenReturn(bidList);

        String viewName = bidListController.updateBid(null, bidList, result, model); 
     
        verify(model).addAttribute("bidList", bidList); // Je vérifie l'ajout au modèle
        verify(bidListService).updateBidList(bidList); // Je vérifie que le service a été appelé
        assertEquals("redirect:/bidList/list", viewName); // Je vérifie que la vue retournée est correcte
    }
    
    @Test
    public void testDeleteSansErreurs() {
    	Integer id = 1;

        doNothing().when(bidListService).deleteBidListById(id);
        String viewName = bidListController.deleteBid(id, model);
        verify(bidListService).deleteBidListById(id); // Je vérifie que le service a été appelé
        assertEquals("redirect:/bidList/list", viewName); // Je vérifie que la vue retournée est correcte
    }
    

}
