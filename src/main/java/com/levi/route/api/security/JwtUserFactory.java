package com.levi.route.api.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.levi.route.api.entity.User;
import com.levi.route.api.enun.Role;


public class JwtUserFactory {

	private JwtUserFactory() {
	}

	public static JwtUser create(User user) {
		return new JwtUser(user.getId(), user.getUsername(), user.getPassword(), mapToGrantedAuthorities(user.getRole()));
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(Role userEnum) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(userEnum.toString()));
		return authorities;
	}

}
