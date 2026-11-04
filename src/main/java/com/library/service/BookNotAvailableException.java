package com.library.service;

/**
 * Thrown when trying to borrow a book that is already borrowed.
 */
public class BookNotAvailableException extends RuntimeException {
	public BookNotAvailableException(String bookId) {
		super("Book is not available for borrowing: " + bookId);
	}
}