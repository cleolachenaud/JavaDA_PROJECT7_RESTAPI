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
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class RuleServiceTest {

	@Mock
    private RuleNameRepository ruleNameRepository;

    @InjectMocks
    private RuleNameService ruleNameService; // Classe A TESTER

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testUpdateRuleNameException() {
    	RuleName ruleName = new RuleName();
        
        when(ruleNameRepository.existsById(ruleName.getId())).thenReturn(false);
        
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
        	ruleNameService.updateRuleName(ruleName);
        });

        assertEquals(ConstantesUtils.RULENAME_NOTFOUND, thrown.getMessage());
    }

    @Test
    public void testUpdateRuleNameOk() {
    	RuleName ruleName = new RuleName();
    	ruleName.setId(1);
               
        
        when(ruleNameRepository.existsById(ruleName.getId())).thenReturn(true);
        when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);
        
        RuleName result = ruleNameService.updateRuleName(ruleName);
        
        verify(ruleNameRepository).save(ruleName);
        assertNotNull(result);
        assertEquals(ruleName, result);
    }
    @Test
    public void testDeleteRuleNameException() {
    	RuleName ruleName = new RuleName();
        
        when(ruleNameRepository.findById(ruleName.getId())).thenReturn(Optional.empty());
        
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
        	ruleNameService.deleteRuleNameById(ruleName.getId());
        });

        assertEquals(ConstantesUtils.RULENAME_NOTFOUND, thrown.getMessage());
    }
    @Test
    public void testDeleteRuleNameByIdOk() {
        Integer id = 1;
        when(ruleNameRepository.findById(id)).thenReturn(Optional.of(new RuleName()));

        ruleNameService.deleteRuleNameById(id);

        verify(ruleNameRepository).deleteById(id);
    }
    
    @Test
    public void testAddRuleNameByIdOk() {
    	RuleName ruleName = new RuleName();
        ruleName.setId(1);
        
        when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);
        
        RuleName result = ruleNameService.addRuleName(ruleName);
        
        verify(ruleNameRepository).save(ruleName);
        assertNotNull(result);
        assertEquals(ruleName, result);
    	
    }
    
}
