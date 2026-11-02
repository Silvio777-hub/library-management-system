package com.library.transaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class PostgresTransactionManagerTest {

	@Container
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

	private ConnectionFactory connectionFactory;
	private TransactionManager txManager;

	@BeforeEach
	void setUp() throws Exception {
		connectionFactory = () -> DriverManager.getConnection(postgres.getJdbcUrl(), postgres.getUsername(),
				postgres.getPassword());

		txManager = new PostgresTransactionManager(connectionFactory);

		// Arrange: Create a clean test table for each test run
		try (Connection conn = connectionFactory.getConnection(); Statement stmt = conn.createStatement()) {
			stmt.execute("DROP TABLE IF EXISTS test_table");
			stmt.execute("CREATE TABLE test_table (id VARCHAR(10) PRIMARY KEY)");
		}
	}

	@Test
	void whenCodeFails_thenRollbackOccurs() throws Exception {
		// Act: Execute a transaction that inserts a row, but then throws an exception
		assertThrows(RuntimeException.class, () -> {
			txManager.doInTransaction(conn -> {
				try (Statement stmt = conn.createStatement()) {
					stmt.execute("INSERT INTO test_table (id) VALUES ('A1')");
				}
				throw new RuntimeException("Simulated failure");
			});
		});

		// Assert: The row should NOT exist because the transaction rolled back
		try (Connection conn = connectionFactory.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM test_table")) {
			rs.next();
			long count = rs.getLong(1);
			assertThat(count).isZero();
		}
	}
}