package com.library.domain;

/**
 * A book in the library catalogue.
 */
public record Book(String id, String title, String author, String isbn, BookStatus status) {

	/**
	 * Returns true when this book can be borrowed.
	 */
	public boolean isAvailable() {
		return status == BookStatus.AVAILABLE;
	}
}