package com.library.domain;

import java.util.regex.Pattern;

/**
 * Unique registration number of a student.
 */
public record StudentRegistrationNumber(String value) {

	private static final Pattern FORMAT = Pattern.compile("\\d{7}");

	public StudentRegistrationNumber {
		if (!FORMAT.matcher(value).matches()) {
			throw new IllegalArgumentException("Student registration number must be exactly 7 digits: " + value);
		}
	}
}