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


import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
@SpringBootTest
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
        result.reject("error", "controller.rating.erreur"); // Simuler une erreur

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        	ratingController.validate(rating, result, model);
        });
        verifyNoInteractions(ratingService); // Je vérifie que le service n'a pas été appelé
        assertEquals("controller.rating.erreur", exception.getMessage()); // Je vérifie le message d'exception
        
    }

    @Test
    public void testValidateSansErreurs() {

    	Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        BindingResult result = new BeanPropertyBindingResult(rating, "rating");

        when(ratingService.addRating(rating)).thenReturn(rating); 

        String viewName = ratingController.validate(rating, result, model); 
     
        verify(model).addAttribute("rating", rating); // Je vérifie l'ajout au modèle
        verify(ratingService).addRating(rating); // Je vérifie que le service a été appelé
        assertEquals("rating/add", viewName); // Je vérifie que la vue retournée est correcte
    }
}
