package com.nnk.springboot;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

import junit.framework.Assert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)

@SpringBootTest 
@ActiveProfiles("test")
public class UserTest {

	@Autowired
	private UserRepository userRepository;
	
	@BeforeEach
    public void setUp() {
        userRepository.deleteAll(); // nettoie la base de données avant chaque test
    }

	@Test
	public void UserTests() {
	    User user = new User("Username Test", "Password Test", "Fullname Test", "Role Test");

        // Save
        user = userRepository.save(user);
        assertNotNull(user.getId());
        assertEquals(user.getUsername(), "Username Test");

        // Update
        user.setFullname("Updated Fullname");
        user = userRepository.save(user);
        assertEquals(user.getFullname(), "Updated Fullname");

        // Find
        List<User> listResult = userRepository.findAll();
        assertTrue(listResult.size() > 0);

        // Delete
        Integer id = user.getId();
        userRepository.delete(user);
        Optional<User> userList = userRepository.findById(id);
        assertFalse(userList.isPresent());
	}
}
