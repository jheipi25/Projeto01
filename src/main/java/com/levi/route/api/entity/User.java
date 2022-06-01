package com.levi.route.api.entity;

import java.security.NoSuchAlgorithmException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.levi.route.api.dto.UserDto;
import com.levi.route.api.enun.Role;
import com.levi.route.api.util.PasswordUtils;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private Role role;
	
	public User() {
		
	}
	
	public static User fromDto(UserDto userDto) throws NoSuchAlgorithmException {
		User user = new User();
		
		user.setUsername(userDto.getUsername());
		user.setPassword(PasswordUtils.generateBCrypt(userDto.getPassword()));
		user.setRole(Role.ROLE_ADMIN);
		
		return user;
	}
	
}
