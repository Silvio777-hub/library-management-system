package com.library.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import com.library.domain.User;

public interface UserRepository {
	Optional<User> findByUsername(Connection conn, String username) throws SQLException;

	void save(Connection conn, User user) throws SQLException;
}