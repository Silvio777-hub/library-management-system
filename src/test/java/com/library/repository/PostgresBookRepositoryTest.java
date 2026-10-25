package com.library.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.library.domain.Book;
import com.library.domain.BookStatus;

@Testcontainers
class PostgresBookRepositoryTest {

	@Container
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
			.withInitScript("schema.sql");

	private Connection conn;
	private BookRepository repository;

	@BeforeEach
	void setUp() throws Exception {
		conn = DriverManager.getConnection(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
		repository = new PostgresBookRepository();
	}

	@Test
	void saveAndFindById_worksCorrectly() throws Exception {
		Book book = new Book("B1", "Clean Code", "Robert C. Martin", "978-0132350884", BookStatus.AVAILABLE);

		repository.save(conn, book);
		Book loaded = repository.findById(conn, "B1").orElseThrow();

		assertThat(loaded.id()).isEqualTo("B1");
		assertThat(loaded.title()).isEqualTo("Clean Code");
		assertThat(loaded.status()).isEqualTo(BookStatus.AVAILABLE);
	}
}