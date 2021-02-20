package com.codpix.domain.error;

public class MissingMandatoryValueException extends CodPixException {
	
	public MissingMandatoryValueException(final String message) {
		super(message);
	}
	
	private static String defaultMessage(String fieldName) {
		return String.format("The field %s is mandatory and wasn't set", fieldName);
	}
	
	public static MissingMandatoryValueException forBlankValue(String fieldName) {
		return new MissingMandatoryValueException(defaultMessage(fieldName) + " (blank)");
	}
	
	public static MissingMandatoryValueException forNullValue(String fieldName) {
		return new MissingMandatoryValueException(defaultMessage(fieldName) + " (null)");
	}
}
