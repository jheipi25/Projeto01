package com.levi.route.api.service;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.levi.route.api.entity.User;
import com.levi.route.api.repository.UserRepository;

@Service
@Slf4j
public class UserService {
	
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User persist(User user) {
		log.info("Persisting user: {}", user);
		return this.userRepository.save(user);
	}

	public Optional<User> findByUsername(String username) {
		log.info("Finding user by username {}", username);
		return Optional.ofNullable(this.userRepository.findByUsername(username));
	}

}
