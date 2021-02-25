package com.codpix.domain.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum StandardMessage implements CodPixMessage {
	
	BANK_ALREADY_REGISTERED("user.bank-already-registered"),
	INTERNAL_SERVER_ERROR("server.internal-server-error"),
	STRING_LENGTH_TOO_LONG("server.string-too-long"),
	FIELD_MANDATORY_NULL("server.mandatory-null"),
	FIELD_MANDATORY_BLANK("server.mandatory-blank"),
	STRING_LENGTH_NOT_EQUAL("server.string-enot-qual");
	
	@Getter
	private final String messageKey;
}
