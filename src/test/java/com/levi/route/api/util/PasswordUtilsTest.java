package com.levi.route.api.util;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.levi.route.api.util.PasswordUtils;

public class PasswordUtilsTest {
	
	private static final String PASSWORD = "123456";
	private final BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();

	@Test
	public void testSenhaNula() throws Exception {
		assertNull(PasswordUtils.generateBCrypt(null));
	}
	
	@Test
	public void testGerarHashSenha() throws Exception {
		String hash = PasswordUtils.generateBCrypt(PASSWORD);
		
		assertTrue(bCryptEncoder.matches(PASSWORD, hash));
	}

}
