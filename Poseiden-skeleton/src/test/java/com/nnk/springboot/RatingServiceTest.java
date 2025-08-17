package com.nnk.springboot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.nnk.springboot.configuration.ConstantesUtils;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class RatingServiceTest {

	@Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingService ratingService; // Classe A TESTER

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testUpdateRatingException() {
    	Rating rating = new Rating();
        
        when(ratingRepository.existsById(rating.getId())).thenReturn(false);
        
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
        	ratingService.updateRating(rating);
        });

        assertEquals(ConstantesUtils.RATING_NOTFOUND, thrown.getMessage());
    }

    @Test
    public void testUpdateRatingOk() {
    	Rating rating = new Rating();
    	rating.setId(1);
               
        
        when(ratingRepository.existsById(rating.getId())).thenReturn(true);
        when(ratingRepository.save(rating)).thenReturn(rating);
        
        Rating result = ratingService.updateRating(rating);
        
        verify(ratingRepository).save(rating);
        assertNotNull(result);
        assertEquals(rating, result);
    }
    @Test
    public void testDeleteRatingException() {
    	Rating rating = new Rating();
        
        when(ratingRepository.findById(rating.getId())).thenReturn(Optional.empty());
        
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
        	ratingService.deleteRatingById(rating.getId());
        });

        assertEquals(ConstantesUtils.RATING_NOTFOUND, thrown.getMessage());
    }
    
    @Test
    public void testDeleteRatingByIdOk() {
        Integer id = 1;
        when(ratingRepository.findById(id)).thenReturn(Optional.of(new Rating()));

        ratingService.deleteRatingById(id);

        verify(ratingRepository).deleteById(id);
    }
    
    @Test
    public void testAddCurvePointByIdOk() {
    	Rating rating = new Rating();
        rating.setId(1);
        
        when(ratingRepository.save(rating)).thenReturn(rating);
        
        Rating result = ratingService.addRating(rating);
        
        verify(ratingRepository).save(rating);
        assertNotNull(result);
        assertEquals(rating, result);
    	
    }

    
}
