package com.library.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link StudentRegistrationNumber}.
 */
class StudentRegistrationNumberTest {

	@Test
	void sevenDigits_isAccepted() {
		StudentRegistrationNumber regNo = new StudentRegistrationNumber("7172981");

		assertThat(regNo.value()).isEqualTo("7172981");
	}

	@Test
	void tooShort_isRejected() {
		assertThatIllegalArgumentException().isThrownBy(() -> new StudentRegistrationNumber("123456"));
	}

	@Test
	void containsLetters_isRejected() {
		assertThatIllegalArgumentException().isThrownBy(() -> new StudentRegistrationNumber("71729AB"));
	}
}