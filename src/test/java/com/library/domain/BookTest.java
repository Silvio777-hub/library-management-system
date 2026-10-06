package com.library.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Book}.
 */
class BookTest {

	@Test
	void availableBook_isAvailable() {
		Book book = new Book("B1", "Clean Code", "Robert C. Martin", "978-0132350884", BookStatus.AVAILABLE);

		assertThat(book.isAvailable()).isTrue();
	}

	@Test
	void borrowedBook_isNotAvailable() {
		Book book = new Book("B1", "Clean Code", "Robert C. Martin", "978-0132350884", BookStatus.BORROWED);

		assertThat(book.isAvailable()).isFalse();
	}
}