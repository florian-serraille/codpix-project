package com.codpixproject.codpix.domain.error;

public class MissingMandatoryValueException extends CodPixException {
	
	protected MissingMandatoryValueException(CodPixExceptionBuilder builder) {
		super(builder);
	}
	
	private static CodPixExceptionBuilder builder(CodPixMessage codPixMessage, String fieldName, String message) {
		
		return CodPixException.builder(codPixMessage)
		                      .status(ErrorStatus.INTERNAL_SERVER_ERROR)
		                      .argument("field", fieldName)
		                      .message(message);
	}
	
	private static String defaultMessage(String fieldName) {
		return "The field \"" + fieldName + "\" is mandatory and wasn't set";
	}
	
	public static MissingMandatoryValueException forNullValue(String fieldName) {
		return new MissingMandatoryValueException(
				builder(StandardMessage.FIELD_MANDATORY_NULL, fieldName, defaultMessage(fieldName) + " (null)")
		);
	}
	
	public static MissingMandatoryValueException forBlankValue(String fieldName) {
		return new MissingMandatoryValueException(
				builder(StandardMessage.FIELD_MANDATORY_BLANK, fieldName, defaultMessage(fieldName) + " (blank)")
		);
	}
}
