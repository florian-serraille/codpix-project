package com.codpixproject.codpix.domain.error;

import static java.lang.String.format;

public class StringBadSizeException extends CodPixException {
	
	private StringBadSizeException(final CodPixExceptionBuilder builder) {
		super(builder);
	}
	
	static StringBadSizeException notEqual(final String field, final int expectedLength,
	                                       final int actualLength) {
		
		final String message = format("Length of %s field must be %d but was actually %d",
		                              field, expectedLength, actualLength);
		
		return new StringBadSizeException(CodPixException.builder(StandardMessage.STRING_LENGTH_NOT_EQUAL)
		                                                 .argument("field", field)
		                                                 .argument("actualLength", actualLength)
		                                                 .argument("expectedLength", expectedLength)
		                                                 .message(message)
		                                                 .status(ErrorStatus.INTERNAL_SERVER_ERROR));
	}
	
	static StringBadSizeException tooLong(final String field, final int currentLength, final int maxLength) {
		
		final String message = format("Length of %s field must not exceed %d but was actually %d",
		                              field, maxLength, currentLength);
		
		return new StringBadSizeException(CodPixException.builder(StandardMessage.STRING_LENGTH_TOO_LONG)
		                                                 .argument("field", field)
		                                                 .argument("currentLength", currentLength)
		                                                 .argument("maxLength", maxLength)
		                                                 .message(message)
		                                                 .status(ErrorStatus.INTERNAL_SERVER_ERROR));
	}
}
