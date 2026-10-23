package com.library.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import com.library.domain.Loan;

/**
 * Data access for loans.
 */
public interface LoanRepository {

	void save(Connection conn, Loan loan) throws SQLException;

	Optional<Loan> findById(Connection conn, String id) throws SQLException;
}