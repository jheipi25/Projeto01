package com.levi.route.api.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.levi.route.api.RouteProjectApplication;
import com.levi.route.api.dto.UserDto;
import com.levi.route.api.entity.User;
import com.levi.route.api.enun.Role;
import com.levi.route.api.service.UserService;
import com.levi.route.api.util.PasswordUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RouteProjectApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UserService userService;
	
	private static final String URL_BASE = "/routeProcessor/user/";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	
	@Test
	@WithMockUser
	public void testCadastrarUser() throws Exception {
		User user = getUserData();
		BDDMockito.given(this.userService.persist(Mockito.any(User.class))).willReturn(user);

		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(this.getPostRequisition())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.username").doesNotExist());
	}
	
	private String getPostRequisition() throws JsonProcessingException {
		UserDto userDto = new UserDto();
		userDto.setId(null);
		userDto.setPassword(PasswordUtils.generateBCrypt(PASSWORD));
		userDto.setUsername(USERNAME);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(userDto);
	}

	private User getUserData() {
		User user = new User();
		user.setId(1L);
		user.setPassword(PASSWORD);
		user.setUsername(USERNAME);
		user.setRole(Role.ROLE_USER);
		return user;
	}	
}
