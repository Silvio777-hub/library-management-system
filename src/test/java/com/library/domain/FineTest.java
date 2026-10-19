package com.library.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Fine}.
 */
class FineTest {

	private static final LocalDate CREATED_AT = LocalDate.of(2026, 10, 15);

	private Fine fineWith(FineStatus status, LocalDate paidAt) {
		return new Fine("F1", "L1", new BigDecimal("1.50"), status, CREATED_AT, paidAt);
	}

	@Test
	void unpaid_isNotSettled() {
		Fine fine = fineWith(FineStatus.UNPAID, null);

		assertThat(fine.isSettled()).isFalse();
	}

	@Test
	void paid_isSettled() {
		Fine fine = fineWith(FineStatus.PAID, LocalDate.of(2026, 10, 20));

		assertThat(fine.isSettled()).isTrue();
	}

	@Test
	void waived_isSettled() {
		Fine fine = fineWith(FineStatus.WAIVED, LocalDate.of(2026, 10, 20));

		assertThat(fine.isSettled()).isTrue();
	}
}