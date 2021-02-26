package com.codpixproject.codpix.application.entrypoint.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Schema(description = "Error result for a REST call")
public class CodPixError {
	
	@Schema(name = "Http erro code description", example = "BAD REQUEST", required = true)
	private final String codeDescription;
	
	@Schema(name = "Technical type of this error", example = "user.mandatory", required = true)
	private final String errorType;
	
	@Schema(
			name = "Human readable error message",
			example = "Une erreur technique est survenue lors du traitement de votre demande",
			required = true)
	private final String message;
	
	@Schema(name = "Request path", example = "http://localhost:8080/bank", required = true)
	private final String path;
	
	@Schema(name = "Request timestamp",
	        format = "ISO_OFFSET_DATE_TIME",
	        example = "2011-12-03T10:15:30+01:00",
	        required = true)
	private final ZonedDateTime timestamp;
	
	@Schema(name = "Invalid fields")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<CodPixFieldError> fieldsErrors;
	
}

