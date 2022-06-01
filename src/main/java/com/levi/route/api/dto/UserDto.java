package com.levi.route.api.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDto {

	
	private Long id;

	@NotNull
	private String username;

	@NotNull
	private String password;
	
}
