package com.levi.route.api.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.levi.route.api.RouteProjectApplication;
import com.levi.route.api.entity.User;
import com.levi.route.api.repository.UserRepository;
import com.levi.route.api.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RouteProjectApplication.class)
@ActiveProfiles("test")
public class UserServiceTest {

	@MockBean
	private UserRepository userRepository;

	@Autowired
	private UserService userService;
	
	@Before
	public void setUp() throws Exception {
		BDDMockito.given(this.userRepository.save(Mockito.any(User.class))).willReturn(new User());
		BDDMockito.given(this.userRepository.findByUsername(Mockito.anyString())).willReturn(new User());
	}
	
	@Test
	public void persistUserTest() {
		User user = this.userService.persist(new User());
		assertNotNull(user);
	}

	@Test
	public void findUserByUsername() {
		Optional<User> user = this.userService.findByUsername("username");
		assertTrue(user.isPresent());
	}
}
