package com.library.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.library.domain.Role;
import com.library.domain.User;

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
	void saveAndFindByUsername_worksCorrectly() throws Exception {
		// Arrange
		User user = new User("admin", "hashed_password", Role.ADMIN, true, false);

		// Act
		repository.save(conn, user);
		Optional<User> loaded = repository.findByUsername(conn, "admin");

		// Assert
		assertThat(loaded).isPresent();
		assertThat(loaded.get().username()).isEqualTo("admin");
		assertThat(loaded.get().role()).isEqualTo(Role.ADMIN);
		assertThat(loaded.get().active()).isTrue();
	}

	@Test
	void findByUsername_whenMissing_thenReturnsEmpty() throws Exception {
		// Act
		Optional<User> loaded = repository.findByUsername(conn, "unknown_user");

		// Assert
		assertThat(loaded).isEmpty();
	}
}