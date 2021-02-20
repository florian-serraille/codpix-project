package com.codpix.domain.error;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class Assert {
	
	public static void notNull(String field, Object input) {
		if (input == null) {
			throw MissingMandatoryValueException.forNullValue(field);
		}
	}
	
	public static StringAsserter field(String fieldName, String fieldValue) {
		return new StringAsserter(fieldName, fieldValue);
	}
	
	@RequiredArgsConstructor
	public static class StringAsserter {
		
		private final String fieldName;
		private final String fieldValue;
		
		public StringAsserter notBlank() {
			
			notNull(fieldName, fieldValue);
			
			if (fieldValue.isBlank()) {
				throw MissingMandatoryValueException.forBlankValue(fieldName);
			}
			
			return this;
		}
		
		public StringAsserter length(final int length) {
			
			if (isNull(fieldValue) || fieldValue.length() == length) {
				return this;
			}
			
			throw StringBadSizeException.forNotEqual(fieldName, length, fieldValue.length());
		}
		
		public StringAsserter maxlength(final int length) {
			
			if (isNull(fieldValue) || fieldValue.length() <= length) {
				return this;
			}
			
			throw StringBadSizeException.forTooLong(fieldName, length, fieldValue.length());
		}
	}
}
