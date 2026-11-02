package com.library.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.library.domain.Loan;
import com.library.domain.LoanStatus;
import com.library.domain.StudentRegistrationNumber;

/**
 * PostgreSQL implementation of LoanRepository.
 */
public class PostgresLoanRepository implements LoanRepository {

	@Override
	public void save(Connection conn, Loan loan) throws SQLException {
		String sql = "INSERT INTO loan (id, book_id, registration_number, borrowed_at, due_at, returned_at, status) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, loan.id());
			stmt.setString(2, loan.bookId());
			stmt.setString(3, loan.studentRegistrationNumber().value());
			stmt.setDate(4, Date.valueOf(loan.borrowedAt()));
			stmt.setDate(5, Date.valueOf(loan.dueAt()));

			if (loan.returnedAt() != null) {
				stmt.setDate(6, Date.valueOf(loan.returnedAt()));
			} else {
				stmt.setNull(6, java.sql.Types.DATE);
			}

			stmt.setString(7, loan.status().name());

			stmt.executeUpdate();
		}
	}

	@Override
	public Optional<Loan> findById(Connection conn, String id) throws SQLException {
		String sql = "SELECT id, book_id, registration_number, borrowed_at, due_at, returned_at, status "
				+ "FROM loan WHERE id = ?";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Date returnedDate = rs.getDate("returned_at");

					Loan loan = new Loan(rs.getString("id"), rs.getString("book_id"),
							new StudentRegistrationNumber(rs.getString("registration_number")),
							rs.getDate("borrowed_at").toLocalDate(), rs.getDate("due_at").toLocalDate(),
							returnedDate != null ? returnedDate.toLocalDate() : null,
							LoanStatus.valueOf(rs.getString("status")));
					return Optional.of(loan);
				}
			}
		}
		return Optional.empty();
	}
}