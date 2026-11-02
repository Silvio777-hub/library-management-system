package com.library.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.library.domain.Loan;
import com.library.domain.LoanStatus;
import com.library.domain.StudentRegistrationNumber;

@Testcontainers
class PostgresLoanRepositoryTest {

	@Container
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
			.withInitScript("schema.sql");

	private Connection conn;
	private LoanRepository repository;

	@BeforeEach
	void setUp() throws Exception {
		conn = DriverManager.getConnection(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
		repository = new PostgresLoanRepository();

		try (Statement stmt = conn.createStatement()) {
			stmt.execute(
					"INSERT INTO book (id, title, author, isbn, status) VALUES ('B1', 'Title', 'Author', '123', 'AVAILABLE')");
		}
	}

	@Test
	void saveAndFindById_worksCorrectly() throws Exception {
		Loan loan = new Loan("L1", "B1", new StudentRegistrationNumber("7172981"), LocalDate.of(2026, 10, 1),
				LocalDate.of(2026, 10, 15), null, LoanStatus.ACTIVE);

		repository.save(conn, loan);
		Loan loaded = repository.findById(conn, "L1").orElseThrow();

		assertThat(loaded.id()).isEqualTo("L1");
		assertThat(loaded.status()).isEqualTo(LoanStatus.ACTIVE);
	}
}