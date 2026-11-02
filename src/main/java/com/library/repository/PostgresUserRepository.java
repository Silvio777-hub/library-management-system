package com.library.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.library.domain.StudentRegistrationNumber;

/**
 * PostgreSQL implementation of UserRepository.
 */
public class PostgresUserRepository implements UserRepository {

	@Override
	public boolean exists(Connection conn, StudentRegistrationNumber regNo) throws SQLException {
		String sql = "SELECT 1 FROM app_user WHERE registration_number = ?";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, regNo.value());

			try (ResultSet rs = stmt.executeQuery()) {
				return rs.next(); // Returns true if a row exists
			}
		}
	}
}