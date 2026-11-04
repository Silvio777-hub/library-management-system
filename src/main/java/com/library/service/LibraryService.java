package com.library.service;

import java.time.LocalDate;
import java.util.UUID;

import com.library.domain.Book;
import com.library.domain.BookStatus;
import com.library.domain.Loan;
import com.library.domain.LoanStatus;
import com.library.domain.StudentRegistrationNumber;
import com.library.repository.BookRepository;
import com.library.repository.LoanRepository;
import com.library.transaction.TransactionManager;

/**
 * Business logic for library operations.
 */
public class LibraryService {

	private static final int LOAN_DAYS = 14;

	private final TransactionManager txManager;
	private final BookRepository bookRepository;
	private final LoanRepository loanRepository;

	public LibraryService(TransactionManager txManager, BookRepository bookRepository, LoanRepository loanRepository) {
		this.txManager = txManager;
		this.bookRepository = bookRepository;
		this.loanRepository = loanRepository;
	}

	public void issueLoan(String bookId, StudentRegistrationNumber regNo) {
		txManager.doInTransaction(conn -> {
			Book book = bookRepository.findById(conn, bookId)
					.orElseThrow(() -> new IllegalArgumentException("Book not found: " + bookId));

			if (book.status() != BookStatus.AVAILABLE) {
				throw new BookNotAvailableException(bookId);
			}

			// 1. Update book status
			bookRepository.updateStatus(conn, bookId, BookStatus.BORROWED.name());

			// 2. Create and save the loan
			LocalDate today = LocalDate.now();
			Loan loan = new Loan(UUID.randomUUID().toString(), bookId, regNo, today, today.plusDays(LOAN_DAYS), null,
					LoanStatus.ACTIVE);
			loanRepository.save(conn, loan);

			return null; // TransactionCode requires a return value
		});
	}
}