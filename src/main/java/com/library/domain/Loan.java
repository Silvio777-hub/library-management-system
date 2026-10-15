package com.library.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A loan of one book to one student.
 */
public record Loan(String id, String bookId, StudentRegistrationNumber studentRegistrationNumber, LocalDate borrowedAt,
		LocalDate dueAt, LocalDate returnedAt, LoanStatus status) {

	public static final BigDecimal FINE_PER_DAY = new BigDecimal("0.50");

	/**
	 * Returns true when the loan is active and past its due date. STUB - always
	 * returns false, so the RED test fails on purpose.
	 */
	public boolean isOverdue(LocalDate today) {
		return false;
	}

	/**
	 * Calculates the fine for a late return: daysLate * FINE_PER_DAY. STUB - always
	 * returns zero, so the RED test fails on purpose.
	 */
	public BigDecimal calculateFine(LocalDate returnDate) {
		return BigDecimal.ZERO;
	}
}