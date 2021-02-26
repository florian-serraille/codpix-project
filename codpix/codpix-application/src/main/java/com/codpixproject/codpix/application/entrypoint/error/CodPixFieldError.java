package com.codpixproject.codpix.application.entrypoint.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CodPixFieldError {
	
	@Schema(name = "Path to the field in error", example = "address.country", required = true)
	private final String fieldPath;
	
	@Schema(name = "Technical reason for the invalidation", example = "user.mandatory", required = true)
	private final String reason;
	
	@Schema(name = "Human readable message for the invalidation", example = "Field must be provided", required = true)
	private final String message;
}