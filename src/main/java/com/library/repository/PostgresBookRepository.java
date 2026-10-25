package com.library.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.library.domain.Book;
import com.library.domain.BookStatus;

/**
 * PostgreSQL implementation of BookRepository.
 */
public class PostgresBookRepository implements BookRepository {

	@Override
	public void save(Connection conn, Book book) throws SQLException {
		String sql = "INSERT INTO book (id, title, author, isbn, status) VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, book.id());
			stmt.setString(2, book.title());
			stmt.setString(3, book.author());
			stmt.setString(4, book.isbn());
			stmt.setString(5, book.status().name());

			stmt.executeUpdate();
		}
	}

	@Override
	public Optional<Book> findById(Connection conn, String id) throws SQLException {
		String sql = "SELECT id, title, author, isbn, status FROM book WHERE id = ?";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Book book = new Book(rs.getString("id"), rs.getString("title"), rs.getString("author"),
							rs.getString("isbn"), BookStatus.valueOf(rs.getString("status")));
					return Optional.of(book);
				}
			}
		}
		return Optional.empty();
	}

	@Override
	public void updateStatus(Connection conn, String id, String newStatus) throws SQLException {
		String sql = "UPDATE book SET status = ? WHERE id = ?";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, newStatus);
			stmt.setString(2, id);

			stmt.executeUpdate();
		}
	}
}