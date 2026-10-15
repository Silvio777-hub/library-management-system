package com.library.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Loan}.
 */
class LoanTest {

	private static final LocalDate BORROWED_AT = LocalDate.of(2026, 10, 1);
	private static final LocalDate DUE_AT = LocalDate.of(2026, 10, 14);

	private Loan activeLoan() {
		return new Loan("L1", "B1", new StudentRegistrationNumber("7172981"), BORROWED_AT, DUE_AT, null,
				LoanStatus.ACTIVE);
	}

	@Test
	void onTimeReturn_hasNoFine() {
		Loan loan = activeLoan();

		assertThat(loan.calculateFine(LocalDate.of(2026, 10, 14))).isEqualByComparingTo("0.00");
	}

	@Test
	void oneDayLate_hasFineOfOneDay() {
		Loan loan = activeLoan();

		assertThat(loan.calculateFine(LocalDate.of(2026, 10, 15))).isEqualByComparingTo("0.50");
	}

	@Test
	void activeAndPastDue_isOverdue() {
		Loan loan = activeLoan();

		assertThat(loan.isOverdue(LocalDate.of(2026, 10, 15))).isTrue();
	}

	@Test
	void activeAndOnDueDate_isNotOverdue() {
		Loan loan = activeLoan();

		assertThat(loan.isOverdue(LocalDate.of(2026, 10, 14))).isFalse();
	}

	@Test
	void returned_isNotOverdue() {
		Loan returned = new Loan("L1", "B1", new StudentRegistrationNumber("7172981"), BORROWED_AT, DUE_AT,
				LocalDate.of(2026, 10, 15), LoanStatus.RETURNED);

		assertThat(returned.isOverdue(LocalDate.of(2026, 10, 20))).isFalse();
	}
}