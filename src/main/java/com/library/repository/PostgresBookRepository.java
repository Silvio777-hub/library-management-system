package com.library.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import com.library.domain.Book;

public class PostgresBookRepository implements BookRepository {

	@Override
	public void save(Connection conn, Book book) throws SQLException {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public Optional<Book> findById(Connection conn, String id) throws SQLException {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public void updateStatus(Connection conn, String id, String newStatus) throws SQLException {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}