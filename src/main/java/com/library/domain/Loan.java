package com.library.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * A loan of one book to one student.
 */
public record Loan(String id, String bookId, StudentRegistrationNumber studentRegistrationNumber, LocalDate borrowedAt,
		LocalDate dueAt, LocalDate returnedAt, LoanStatus status) {

	public static final BigDecimal FINE_PER_DAY = new BigDecimal("0.50");

	/**
	 * Returns true when the loan is active and past its due date.
	 */
	public boolean isOverdue(LocalDate today) {
		return status == LoanStatus.ACTIVE && today.isAfter(dueAt);
	}

	/**
	 * Calculates the fine for a late return: daysLate * FINE_PER_DAY. Returns zero
	 * if returned on or before the due date.
	 */
	public BigDecimal calculateFine(LocalDate returnDate) {
		if (!returnDate.isAfter(dueAt)) {
			return BigDecimal.ZERO;
		}
		long daysLate = ChronoUnit.DAYS.between(dueAt, returnDate);
		return FINE_PER_DAY.multiply(BigDecimal.valueOf(daysLate));
	}
}