package com.codpixproject.codpix.application.entrypoint.error;

import com.codpixproject.codpix.domain.error.CodPixMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor
public enum ApplicationMessage implements CodPixMessage {
	
	BAD_LENGTH("user.bad-length"),
	BAD_REQUEST(ValidationMessage.BAD_REQUEST),
	MUST_BE_NOT_NULL(ValidationMessage.MUST_BE_NOT_NULL),
	MUST_BE_NULL(ValidationMessage.MUST_BE_NULL);
	
	@Getter
	private final String messageKey;
	
	public static boolean isAValidKey(final String messageKey) {
		
		return Arrays.stream(ApplicationMessage.values())
		             .anyMatch(applicationMessage ->
				                       applicationMessage.getMessageKey().equals(messageKey));
	}
	
	@NoArgsConstructor(access = PRIVATE)
	public static final class ValidationMessage {
		
		public static final String BAD_LENGTH = "user.must-be-null";
		public static final String BAD_REQUEST = "user.bad-request";
		public static final String MANDATORY = "user.mandatory";
		public static final String MUST_BE_NOT_NULL = "user.must-be-null";
		public static final String MUST_BE_NULL = "user.must-be-null";
		public static final String VALUE_TOO_HIGH = "user.too-high";
		public static final String VALUE_TOO_LOW = "user.too-low";
		public static final String WRONG_FORMAT = "user.wrong-format";
	}
}
