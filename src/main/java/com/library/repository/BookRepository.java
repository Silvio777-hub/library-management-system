package com.library.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import com.library.domain.Book;

/**
 * Data access for books.
 */
public interface BookRepository {

	void save(Connection conn, Book book) throws SQLException;

	Optional<Book> findById(Connection conn, String id) throws SQLException;

	void updateStatus(Connection conn, String id, String newStatus) throws SQLException;
}