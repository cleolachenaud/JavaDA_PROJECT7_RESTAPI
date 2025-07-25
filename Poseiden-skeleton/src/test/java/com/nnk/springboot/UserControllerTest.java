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
import org.springframework.test.context.ActiveProfiles;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTest {
	

	@Mock
    private UserService userService; 
    @Mock
    private Model model; 
    @InjectMocks
    private UserController userController;

    public UserControllerTest() {
        MockitoAnnotations.openMocks(this); 
         
    }

   

    @Test
    public void testAddUserForm() {

    	User user = new User(); 
        String viewName = userController.addUser(user); 
        assertEquals("user/add", viewName); 
    }
    

}
