package com.codpixproject.codpix.application.entrypoint.error;

import com.codpixproject.codpix.application.entrypoint.TestJson;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CodPixFieldErrorTest {
	
	@Test
	public void shouldGetFieldErrorInformation() {
		var fieldError = defaultCodPixFieldError();
		
		assertThat(fieldError.getFieldPath()).isEqualTo("fieldPath");
		assertThat(fieldError.getReason()).isEqualTo("reason");
		assertThat(fieldError.getMessage()).isEqualTo("message");
	}
	
	
	@Test
	void shouldSerializeAllFields() {
		
		// Given
		final var codpixFieldError = defaultCodPixFieldError();
		
		// When
		final var json = TestJson.writeAsString(codpixFieldError);
		
		// Then
		Assertions.assertThat(json).isEqualTo(defaultJson());
	}
	
	static CodPixFieldError defaultCodPixFieldError() {
		return new CodPixFieldError("fieldPath", "reason", "message");
	}
	
	static String defaultJson() {
		return "{\"fieldPath\":\"fieldPath\",\"reason\":\"reason\",\"message\":\"message\"}";
	}
	
}