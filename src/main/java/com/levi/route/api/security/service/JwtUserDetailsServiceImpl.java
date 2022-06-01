package com.levi.route.api.security.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.levi.route.api.entity.User;
import com.levi.route.api.security.JwtUserFactory;
import com.levi.route.api.service.UserService;


@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	private final UserService userService;

	public JwtUserDetailsServiceImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userService.findByUsername(username);

		if (user.isPresent()) {
			return JwtUserFactory.create(user.get());
		}

		throw new UsernameNotFoundException("Username not founded.");
	}

}
