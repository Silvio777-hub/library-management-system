package com.library.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.library.domain.Role;
import com.library.domain.User;
import com.library.repository.UserRepository;
import com.library.transaction.TransactionCode;
import com.library.transaction.TransactionManager;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	@Mock
	private TransactionManager txManager;
	@Mock
	private UserRepository userRepository;

	private AuthService service;

	@BeforeEach
	void setUp() {
		service = new AuthService(txManager, userRepository);

		lenient().when(txManager.doInTransaction(any())).thenAnswer(invocation -> {
			TransactionCode<?> code = invocation.getArgument(0);
			return code.execute(null);
		});
	}

	@Test
	void login_whenValidCredentials_thenReturnsUser() throws Exception {
		// Arrange
		User user = new User("admin", "hashed_password", Role.ADMIN, true, false);
		lenient().when(userRepository.findByUsername(null, "admin")).thenReturn(Optional.of(user));

		// Act
		User result = service.login("admin", "plain_password");

		// Assert
		assertThat(result).isEqualTo(user);
	}

	@Test
	void login_whenUserNotFound_thenThrowsException() throws Exception {
		// Arrange
		lenient().when(userRepository.findByUsername(null, "unknown")).thenReturn(Optional.empty());

		// Act & Assert
		assertThatThrownBy(() -> service.login("unknown", "password")).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("User not found");
	}
}