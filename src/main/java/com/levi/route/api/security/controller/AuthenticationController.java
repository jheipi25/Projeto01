package com.levi.route.api.security.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levi.route.api.security.dto.JwtAuthenticationDto;
import com.levi.route.api.security.dto.TokenDto;
import com.levi.route.api.security.util.JwtTokenUtil;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@Slf4j
public class AuthenticationController {

	private static final String TOKEN_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";

	private final JwtTokenUtil jwtTokenUtil;
	private final UserDetailsService userDetailsService;

	public AuthenticationController(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
		this.jwtTokenUtil = jwtTokenUtil;
		this.userDetailsService = userDetailsService;
	}

	@PostMapping
	public TokenDto generateTokenJwt(
			@Valid @RequestBody JwtAuthenticationDto authenticationDto)
			throws AuthenticationException {
		log.info("Generating token for username {}.", authenticationDto.getUsername());
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDto.getUsername());
		String token = jwtTokenUtil.getToken(userDetails);

		return new TokenDto(token);
	}

	@PostMapping(value = "/refresh")
	public TokenDto generateRefreshTokenJwt(HttpServletRequest request) {
		log.info("Generating refresh token JWT.");
		Optional<String> token = Optional.ofNullable(request.getHeader(TOKEN_HEADER));
		
		if (token.isPresent() && token.get().startsWith(BEARER_PREFIX)) {
			token = Optional.of(token.get().substring(7));
        }
		
		String refreshedToken = jwtTokenUtil.refreshToken(token.get());
		return new TokenDto(refreshedToken);
	}

}
