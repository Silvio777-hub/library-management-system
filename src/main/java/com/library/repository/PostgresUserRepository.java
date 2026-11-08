package com.library.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.library.domain.Role;
import com.library.domain.User;

public class PostgresUserRepository implements UserRepository {

	@Override
	public Optional<User> findByUsername(Connection conn, String username) throws SQLException {
		String sql = "SELECT username, password_hash, role, active, must_change_password FROM app_user WHERE username = ?";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, username);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					User user = new User(rs.getString("username"), rs.getString("password_hash"),
							Role.valueOf(rs.getString("role")), rs.getBoolean("active"),
							rs.getBoolean("must_change_password"));
					return Optional.of(user);
				}
			}
		}
		return Optional.empty();
	}

	@Override
	public void save(Connection conn, User user) throws SQLException {
		String sql = "INSERT INTO app_user (username, password_hash, role, active, must_change_password) VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, user.username());
			stmt.setString(2, user.passwordHash());
			stmt.setString(3, user.role().name());
			stmt.setBoolean(4, user.active());
			stmt.setBoolean(5, user.mustChangePassword());

			stmt.executeUpdate();
		}
	}
}