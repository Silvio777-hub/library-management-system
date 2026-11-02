package com.library.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.library.domain.StudentRegistrationNumber;

@Testcontainers
class PostgresUserRepositoryTest {

	@Container
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
			.withInitScript("schema.sql");

	private Connection conn;
	private UserRepository repository;

	@BeforeEach
	void setUp() throws Exception {
		conn = DriverManager.getConnection(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
		repository = new PostgresUserRepository();
	}

	@Test
	void exists_returnsTrueForExistingUser() throws Exception {
		try (Statement stmt = conn.createStatement()) {
			stmt.execute(
					"INSERT INTO app_user (registration_number, full_name, role, active) VALUES ('7172981', 'Silvio', 'STUDENT', true)");
		}

		boolean exists = repository.exists(conn, new StudentRegistrationNumber("7172981"));

		assertThat(exists).isTrue();
	}

	@Test
	void exists_returnsFalseForMissingUser() throws Exception {
		boolean exists = repository.exists(conn, new StudentRegistrationNumber("9999999"));

		assertThat(exists).isFalse();
	}
}