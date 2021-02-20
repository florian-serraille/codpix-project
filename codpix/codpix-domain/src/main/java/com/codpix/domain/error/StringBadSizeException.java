package com.codpix.domain.error;

import static java.lang.String.format;

public class StringBadSizeException extends CodPixException {
	
	protected StringBadSizeException(final String message) {
		super(message);
	}
	
	static StringBadSizeException forNotEqual(final String fieldName, final int expectedLength,
	                                          final int actualLength) {
		
		final String message = format("Length of %s field must be %d but was actually %d",
		                              fieldName, expectedLength, actualLength);
		
		return new StringBadSizeException(message);
	}
	
	static StringBadSizeException forTooLong(final String fieldName, final int expectedLength, final int actualLength) {
		
		final String message = format("Length of %s field must not exceed %d but was actually %d",
		                              fieldName, expectedLength, actualLength);
		
		return new StringBadSizeException(message);
	}
}
