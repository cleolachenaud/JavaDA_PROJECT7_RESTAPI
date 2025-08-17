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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.nnk.springboot.configuration.ConstantesUtils;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import com.nnk.springboot.validation.ValidationPassword;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserServiceTest {

    @InjectMocks
    private UserService userService; // Classe A TESTER
	@Mock
    private UserRepository userRepository;
	@Mock
	private ValidationPassword validationPassword;


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testUpdateUserException() {
    	User user = new User();
        
        when(userRepository.existsById(user.getId())).thenReturn(false);
        
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
        	userService.updateUser(user);
        });

        assertEquals(ConstantesUtils.USER_NOTFOUND, thrown.getMessage());
    }

    @Test
    public void testUpdateUserOk() {
    	User user = new User();
    	user.setId(1);
               
        
        when(userRepository.existsById(user.getId())).thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);
        
        User result = userService.updateUser(user);
        
        verify(userRepository).save(user);
        assertNotNull(result);
        assertEquals(user, result);
    }
    @Test
    public void testDeleteUserException() {
    	User user = new User();
        
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
        	userService.deleteUserById(user.getId());
        });

        assertEquals(ConstantesUtils.USER_NOTFOUND, thrown.getMessage());
    }
    @Test
    public void testDeleteUserByIdOk() {
        Integer id = 1;
        when(userRepository.findById(id)).thenReturn(Optional.of(new User()));

        userService.deleteUserById(id);

        verify(userRepository).deleteById(id);
    }
    
    @Test
    public void testAddUserByIdOk() {
    	User user = new User();
    	user.setId(1);

        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        when(validationPassword.validationEtEncodagePassword(user)).thenReturn("Azerty1&");
        User result = userService.addUser(user);
        

        assertNotNull(result);
        assertEquals(user, result);
    	
    }
    
}
