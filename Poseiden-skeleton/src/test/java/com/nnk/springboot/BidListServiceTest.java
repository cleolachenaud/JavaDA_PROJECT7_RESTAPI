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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;


@SpringBootTest
//@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
public class BidListServiceTest {

	@Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListService bidListService; // Classe A TESTER

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testUpdateBidListException() {
    	BidList bidList = new BidList();
        
        when(bidListRepository.existsById(bidList.getBidListId())).thenReturn(false);
        
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
        	bidListService.updateBidList(bidList);
        });

        assertEquals("service.bidlist.notfound", thrown.getMessage());
    }

    @Test
    public void testUpdateBidListOk() {
    	BidList bidList = new BidList();
    	bidList.setBidListId(1);
               
        
        when(bidListRepository.existsById(bidList.getBidListId())).thenReturn(true);
        when(bidListRepository.save(bidList)).thenReturn(bidList);
        
        BidList result = bidListService.updateBidList(bidList);
        
        verify(bidListRepository).save(bidList);
        assertNotNull(result);
        assertEquals(bidList, result);
    }
    @Test
    public void testDeleteBidListException() {
    	BidList bidList = new BidList();
        
        when(bidListRepository.findById(bidList.getBidListId())).thenReturn(Optional.empty());
        
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
        	bidListService.deleteBidListById(bidList.getBidListId());
        });

        assertEquals("service.bidlist.notfound", thrown.getMessage());
    }
    @Test
    public void testDeleteBidListByIdOk() {
        Integer id = 1;
        when(bidListRepository.findById(id)).thenReturn(Optional.of(new BidList()));

        bidListService.deleteBidListById(id);

        verify(bidListRepository).deleteById(id);
    }
    
    @Test
    public void testAddBidListByIdOk() {
    	BidList bidList = new BidList();
    	bidList.setBidListId(1);
        
        when(bidListRepository.save(bidList)).thenReturn(bidList);
        
        BidList result = bidListService.addBidList(bidList);
        
        verify(bidListRepository).save(bidList);
        assertNotNull(result);
        assertEquals(bidList, result);
    	
    }
    
}
