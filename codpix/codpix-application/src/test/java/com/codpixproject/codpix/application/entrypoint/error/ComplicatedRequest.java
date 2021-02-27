package com.codpixproject.codpix.application.entrypoint.error;

import com.codpixproject.codpix.application.entrypoint.error.ApplicationMessage.ValidationMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.Pattern;

import static com.codpixproject.codpix.application.entrypoint.error.ApplicationMessage.ValidationMessage.*;

public class ComplicatedRequest {
	
	@Getter
	@Pattern(message = WRONG_FORMAT, regexp = "complicated")
	private final String value;
	
	public ComplicatedRequest(@JsonProperty("value") String value) {
		this.value = value;
	}
	
}
