package com.library.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A fine charged to a student for a late return.
 */
public record Fine(String id, String loanId, BigDecimal amount, FineStatus status, LocalDate createdAt,
		LocalDate paidAt) {

	/**
	 * Returns true when this fine has been fully settled.
	 */
	public boolean isSettled() {
		return status == FineStatus.PAID || status == FineStatus.WAIVED;
	}
}