package com.levi.route.api.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
public class PasswordUtils {

	public PasswordUtils() {
	}

	public static String generateBCrypt(String password) {
		if (password == null) {
			return null;
		}

		log.info("Generating hash with BCrypt.");
		BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
		return bCryptEncoder.encode(password);
	}

}

