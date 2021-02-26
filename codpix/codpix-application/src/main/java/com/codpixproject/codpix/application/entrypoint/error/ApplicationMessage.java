package com.codpixproject.codpix.application.entrypoint.error;

import com.codpixproject.codpix.domain.error.CodPixMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ApplicationMessage implements CodPixMessage {
	
	BAD_REQUEST("user.bad-request");
	
	@Getter
	private final String messageKey;
}
