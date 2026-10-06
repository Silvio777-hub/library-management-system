package com.library.domain;

/**
 * A book in the library catalogue.
 */
public record Book(String id, String title, String author, String isbn, BookStatus status) {

	/**
	 * Returns true when this book can be borrowed. STUB - always returns false, so
	 * the RED test fails on purpose.
	 */
	public boolean isAvailable() {
		return false;
	}
}