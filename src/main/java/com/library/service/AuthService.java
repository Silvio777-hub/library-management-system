package com.library.service;

import com.library.domain.User;
import com.library.repository.UserRepository;
import com.library.transaction.TransactionManager;

/**
 * Handles authentication and login.
 */
public class AuthService {
	private final TransactionManager txManager;
	private final UserRepository userRepository;

	public AuthService(TransactionManager txManager, UserRepository userRepository) {
		this.txManager = txManager;
		this.userRepository = userRepository;
	}

	public User login(String username, String password) {
		return txManager.doInTransaction(conn -> {
			User user = userRepository.findByUsername(conn, username)
					.orElseThrow(() -> new IllegalArgumentException("User not found"));

			if (!user.active()) {
				throw new IllegalArgumentException("User is disabled");
			}

			// For now, we are just checking if they exist.
			// In a later PR, we will use BCrypt here to check the password hash.

			return user;
		});
	}
}