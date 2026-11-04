package com.library.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.library.domain.Book;
import com.library.domain.BookStatus;
import com.library.domain.StudentRegistrationNumber;
import com.library.repository.BookRepository;
import com.library.repository.LoanRepository;
import com.library.transaction.TransactionCode;
import com.library.transaction.TransactionManager;

@ExtendWith(MockitoExtension.class)
class LibraryServiceTest {

	@Mock
	private TransactionManager txManager;
	@Mock
	private BookRepository bookRepository;
	@Mock
	private LoanRepository loanRepository;

	private LibraryService service;

	@BeforeEach
	void setUp() {
		service = new LibraryService(txManager, bookRepository, loanRepository);

		// Mock the transaction manager to just execute the code immediately
		lenient().when(txManager.doInTransaction(any())).thenAnswer(invocation -> {
			TransactionCode<?> code = invocation.getArgument(0);
			return code.execute(null); // Pass null connection since repos are mocked
		});
	}

	@Test
	void issueLoan_whenBookAvailable_thenSuccess() throws Exception {
		// Arrange
		Book availableBook = new Book("B1", "Title", "Author", "123", BookStatus.AVAILABLE);
		StudentRegistrationNumber regNo = new StudentRegistrationNumber("7172981");

		// Use lenient() so Mockito doesn't complain if the stub isn't reached during
		// RED phase
		lenient().when(bookRepository.findById(null, "B1")).thenReturn(Optional.of(availableBook));

		// Act
		service.issueLoan("B1", regNo);

		// Assert
		verify(bookRepository).updateStatus(null, "B1", "BORROWED");
		verify(loanRepository).save(eq(null), any());
	}

	@Test
	void issueLoan_whenBookBorrowed_thenThrowsException() throws Exception { // ADDED throws Exception HERE
		// Arrange
		Book borrowedBook = new Book("B1", "Title", "Author", "123", BookStatus.BORROWED);
		StudentRegistrationNumber regNo = new StudentRegistrationNumber("7172981");

		// Use lenient() so Mockito doesn't complain if the stub isn't reached during
		// RED phase
		lenient().when(bookRepository.findById(null, "B1")).thenReturn(Optional.of(borrowedBook));

		// Act & Assert
		assertThatThrownBy(() -> service.issueLoan("B1", regNo)).isInstanceOf(BookNotAvailableException.class);

		// Verify no updates were made
		verify(bookRepository, never()).updateStatus(any(), any(), any());
		verify(loanRepository, never()).save(any(), any());
	}
}