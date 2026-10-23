package com.library.repository;

import java.sql.Connection;
import java.sql.SQLException;

import com.library.domain.StudentRegistrationNumber;

/**
 * Data access for students and staff.
 */
public interface UserRepository {

	boolean exists(Connection conn, StudentRegistrationNumber regNo) throws SQLException;
}