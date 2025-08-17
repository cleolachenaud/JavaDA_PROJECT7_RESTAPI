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
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class TradeServiceTest {

	@Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService; // Classe A TESTER

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testUpdateTradeException() {
    	Trade trade = new Trade();
        
        when(tradeRepository.existsById(trade.getTradeId())).thenReturn(false);
        
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
        	tradeService.updateTrade(trade);
        });

        assertEquals(ConstantesUtils.TRADE_NOTFOUND, thrown.getMessage());
    }

    @Test
    public void testUpdateTradeOk() {
    	Trade trade = new Trade();
    	trade.setTradeId(1);
               
        
        when(tradeRepository.existsById(trade.getTradeId())).thenReturn(true);
        when(tradeRepository.save(trade)).thenReturn(trade);
        
        Trade result = tradeService.updateTrade(trade);
        
        verify(tradeRepository).save(trade);
        assertNotNull(result);
        assertEquals(trade, result);
    }
    @Test
    public void testDeleteTradeException() {
    	Trade trade = new Trade();
        
        when(tradeRepository.findById(trade.getTradeId())).thenReturn(Optional.empty());
        
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
        	tradeService.deleteTradeById(trade.getTradeId());
        });

        assertEquals(ConstantesUtils.TRADE_NOTFOUND, thrown.getMessage());
    }
    @Test
    public void testDeleteTradeByIdOk() {
        Integer id = 1;
        when(tradeRepository.findById(id)).thenReturn(Optional.of(new Trade()));

        tradeService.deleteTradeById(id);

        verify(tradeRepository).deleteById(id);
    }
    
    @Test
    public void testAddTradeByIdOk() {
    	Trade trade = new Trade();
        trade.setTradeId(1);
        
        when(tradeRepository.save(trade)).thenReturn(trade);
        
        Trade result = tradeService.addTrade(trade);
        
        verify(tradeRepository).save(trade);
        assertNotNull(result);
        assertEquals(trade, result);
    	
    }
    
}
