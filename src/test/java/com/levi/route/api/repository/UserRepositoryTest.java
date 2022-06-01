package com.levi.route.api.repository;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.levi.route.api.RouteProjectApplication;
import com.levi.route.api.entity.User;
import com.levi.route.api.enun.Role;
import com.levi.route.api.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RouteProjectApplication.class)
@ActiveProfiles("test")
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	private static final String USERNAME = "Username Test";
	
	@Before
	public void setUp() throws Exception {
		User user = new User();
		user.setUsername("Username Test");
		user.setPassword("123456");
		user.setRole(Role.ROLE_ADMIN);
		this.userRepository.save(user);
	}
	
	@After
    public final void tearDown() { 
		this.userRepository.deleteAll();
	}

	@Test
	public void testBuscarPorCnpj() {
		User user = this.userRepository.findByUsername(USERNAME);
		
		assertEquals(USERNAME, user.getUsername());
	}
}
