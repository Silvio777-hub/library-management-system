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
		throw new UnsupportedOperationException("Not implemented yet");
	}
}