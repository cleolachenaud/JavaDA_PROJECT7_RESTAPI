package com.nnk.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {
	
	 @Autowired
	private MockMvc mockMvc;
	@MockBean
    private UserService userService; 
	@MockBean
    private UserRepository userRepository; 
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
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testSessionAdminAccesUserList() throws Exception {
    	   Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(new User()));
           
           mockMvc.perform(get("/user/list"))
               .andExpect(status().isOk())
               .andExpect(view().name("user/list"))
               .andExpect(model().attributeExists("users"))
               ;
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testValidateSaisieAvecErreur() throws Exception {
        mockMvc.perform(post("/user/validate")
                .param("fullname", "")          
                .param("username", "testUser")  
                .param("password", "")           
                .param("role", "ADMIN"))         
            .andExpect(status().is4xxClientError());
    }
    
 
}
