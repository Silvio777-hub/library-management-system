package com.library.service;

import com.library.domain.StudentRegistrationNumber;
import com.library.repository.BookRepository;
import com.library.repository.LoanRepository;
import com.library.transaction.TransactionManager;

/**
 * Business logic for library operations.
 */
public class LibraryService {

	public LibraryService(TransactionManager txManager, BookRepository bookRepository, LoanRepository loanRepository) {
	}

	public void issueLoan(String bookId, StudentRegistrationNumber regNo) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}