package com.levi.route.api.security.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JwtAuthenticationDto {
	
	private String username;
	private String password;

	public JwtAuthenticationDto() {

	}

}
