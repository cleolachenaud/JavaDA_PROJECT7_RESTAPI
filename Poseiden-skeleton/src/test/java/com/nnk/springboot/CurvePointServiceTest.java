package com.nnk.springboot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.nnk.springboot.configuration.ConstantesUtils;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CurvePointServiceTest {

	@Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private CurvePointService curvePointService; // Classe A TESTER

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testUpdateCurvePointException() {
    	CurvePoint curvePoint = new CurvePoint();
        
        when(curvePointRepository.existsById(curvePoint.getId())).thenReturn(false);
        
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
        	curvePointService.updateCurvePoint(curvePoint);
        });

        assertEquals(ConstantesUtils.CURVEPOINT_NOTFOUND, thrown.getMessage());
    }

    @Test
    public void testUpdateCurvePointOk() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
               
        
        when(curvePointRepository.existsById(curvePoint.getId())).thenReturn(true);
        when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);
        
        CurvePoint result = curvePointService.updateCurvePoint(curvePoint);
        
        verify(curvePointRepository).save(curvePoint);
        assertNotNull(result);
        assertEquals(curvePoint, result);
    }
    @Test
    public void testDeleteCurvePointException() {
    	CurvePoint curvePoint = new CurvePoint();
        
        when(curvePointRepository.findById(curvePoint.getId())).thenReturn(Optional.empty());
        
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
        	curvePointService.deleteCurvePointById(curvePoint.getId());
        });

        assertEquals(ConstantesUtils.CURVEPOINT_NOTFOUND, thrown.getMessage());
    }
    
    @Test
    public void testDeleteCurvePointByIdOk() {
        Integer id = 1;
        when(curvePointRepository.findById(id)).thenReturn(Optional.of(new CurvePoint()));

        curvePointService.deleteCurvePointById(id);

        verify(curvePointRepository).deleteById(id);
    }
    
    @Test
    public void testAddCurvePointByIdOk() {
    	CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        
        when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);
        
        CurvePoint result = curvePointService.addCurvePoint(curvePoint);
        
        verify(curvePointRepository).save(curvePoint);
        assertNotNull(result);
        assertEquals(curvePoint, result);
    	
    }

    
}
