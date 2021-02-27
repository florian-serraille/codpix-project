package com.codpixproject.codpix.domain.bank;

import com.codpixproject.codpix.domain.error.CodPixExceptionAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codpixproject.codpix.domain.error.ErrorStatus.INTERNAL_SERVER_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class BankTest {
	
	@Test
	void shouldBuild() {
		
		// Given
		final var institutionCode = "001";
		final var name = "BB";
		
		// When
		final var throwable = catchThrowable(() -> new Bank(new BankRegistrationRequest(institutionCode, name)));
		
		// Then
		assertThat(throwable).isNull();
	}
	
	@ParameterizedTest(name = "for value {0}")
	@NullSource
	@ValueSource(strings = { "", " ", "\t", "01", "0001" })
	void shouldNotBuildWhenInstitutionIsInvalid(String institutionCode) {
		
		// Given
		final var name = "BB";
		
		// When
		final var throwable = catchThrowable(() -> new Bank(new BankRegistrationRequest(institutionCode, name)));
		
		// Then
		CodPixExceptionAssert.assertThat(throwable).hasStatus(INTERNAL_SERVER_ERROR);
	}
	
	@ParameterizedTest(name = "for value {0}")
	@NullSource
	@ValueSource(strings = { "", " ", "\t", "A", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" })
	void shouldNotBuildWhenNameIsInvalid(String name) {
		
		// Given
		final var institutionCode = "001";
		
		// When
		final var throwable = catchThrowable(() -> new Bank(new BankRegistrationRequest(institutionCode, name)));
		
		// Then
		CodPixExceptionAssert.assertThat(throwable).hasStatus(INTERNAL_SERVER_ERROR);
	}
}