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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.nnk.springboot.configuration.ConstantesUtils;
import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
@SpringBootTest
@ActiveProfiles("test")
public class RatingControllerTest {


	@Mock
    private RatingService ratingService; 
    @Mock
    private Model model; 
    @InjectMocks
    private RatingController ratingController;

    public RatingControllerTest() {
        MockitoAnnotations.openMocks(this); 
         
    }

   

    @Test
    public void testAddRatingForm() {

    	Rating rating = new Rating(); 
        String viewName = ratingController.addRatingForm(rating); 
        assertEquals("rating/add", viewName); 
    }
    @Test
    public void testValidateAvecErreurs() {

    	Rating rating = new Rating(); 
        BindingResult result = new BeanPropertyBindingResult(rating, "rating");
        result.reject("error", ConstantesUtils.RATING_ERREUR); // Simuler une erreur

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        	ratingController.validate(rating, result, model);
        });
        verifyNoInteractions(ratingService); // Je vérifie que le service n'a pas été appelé
        assertEquals(ConstantesUtils.RATING_ERREUR, exception.getMessage()); // Je vérifie le message d'exception
        
    }

    @Test
    public void testValidateSansErreurs() {

    	Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        BindingResult result = new BeanPropertyBindingResult(rating, "rating");

        when(ratingService.addRating(rating)).thenReturn(rating); 

        String viewName = ratingController.validate(rating, result, model); 
     
        verify(model).addAttribute("rating", rating); // Je vérifie l'ajout au modèle
        verify(ratingService).addRating(rating); // Je vérifie que le service a été appelé
        assertEquals("redirect:/rating/list", viewName); // Je vérifie que la vue retournée est correcte
    }
    
    @Test
    public void testValidateUpdateSansErreurs() {

    	Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        BindingResult result = new BeanPropertyBindingResult(rating, "bidList");

        when(ratingService.updateRating(rating)).thenReturn(rating);

        String viewName = ratingController.updateRating(null, rating, result, model); 
     
        verify(model).addAttribute("rating", rating); // Je vérifie l'ajout au modèle
        verify(ratingService).updateRating(rating); // Je vérifie que le service a été appelé
        assertEquals("redirect:/rating/list", viewName); // Je vérifie que la vue retournée est correcte
    }
    
    @Test
    public void testDeleteSansErreurs() {
    	Integer id = 1;

        doNothing().when(ratingService).deleteRatingById(id);
        String viewName = ratingController.deleteBid(id, model);
        verify(ratingService).deleteRatingById(id); // Je vérifie que le service a été appelé
        assertEquals("redirect:/rating/list", viewName); // Je vérifie que la vue retournée est correcte
    }
}
