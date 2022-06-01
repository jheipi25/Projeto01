package com.levi.route.api.controller;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levi.route.api.dto.UserDto;
import com.levi.route.api.entity.User;
import com.levi.route.api.service.UserService;


@RestController
@RequestMapping("/routeProcessor/user")
@CrossOrigin(origins = "*")
@Slf4j
public class UserController {
	
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public UserDto create(@Valid @RequestBody UserDto userDto) throws NoSuchAlgorithmException {
		log.info("Creating user: {}", userDto.toString());

		User user = User.fromDto(userDto);
		this.userService.persist(user);
		
		return userDto;
	}
	
}
