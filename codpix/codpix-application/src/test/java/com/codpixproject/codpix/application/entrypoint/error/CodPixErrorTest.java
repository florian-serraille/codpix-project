package com.codpixproject.codpix.application.entrypoint.error;

import com.codpixproject.codpix.application.entrypoint.TestJson;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;

class CodPixErrorTest {
	
	@Test
	void shouldSerializeAllFieldsLessFieldsError() {
		
		// Given
		final var codpixError = defaultCodPixErrorNoFieldsError();
		
		// When
		final var json = TestJson.writeAsString(codpixError);
		
		// Then
		Assertions.assertThat(json).isEqualTo(defaultJsonWithoutFieldsError());
	}
	
	@Test
	void shouldSerializeAllFields() {
		
		// Given
		final var codpixError = defaultCodPixErrorWithFieldsError();
		
		// When
		final var json = TestJson.writeAsString(codpixError);
		
		// Then
		Assertions.assertThat(json).isEqualTo(defaultJsonWithFieldsError());
	}
	
	private static CodPixError defaultCodPixErrorNoFieldsError() {
		return new CodPixError("description", "error-type", "message", "/api/v1/bank",
		                       ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 12, 0), ZoneId.systemDefault()));
	}
	
	private static String defaultJsonWithoutFieldsError() {
		return "{\"codeDescription\":\"description\",\"errorType\":\"error-type\",\"message\":\"message\",\"path\":\"/api/v1/bank\",\"timestamp\":\"2020-01-01T12:00:00-03:00\"}";
	}
	
	private static CodPixError defaultCodPixErrorWithFieldsError() {
		return new CodPixError("description", "error-type", "message", "/api/v1/bank",
		                       ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 12, 0), ZoneId.systemDefault()),
		                       Collections.singletonList(CodPixFieldErrorTest.defaultCodPixFieldError()));
	}
	
	private static String defaultJsonWithFieldsError() {
		return "{\"codeDescription\":\"description\",\"errorType\":\"error-type\",\"message\":\"message\",\"path\":\"/api/v1/bank\",\"timestamp\":\"2020-01-01T12:00:00-03:00\",\"fieldsErrors\":[" +
		       CodPixFieldErrorTest.defaultJson() + "]}";
	}
}