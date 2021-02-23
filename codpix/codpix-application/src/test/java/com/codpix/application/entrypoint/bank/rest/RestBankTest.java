package com.codpix.application.entrypoint.bank.rest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

class RestBankTest {
	
	private static Validator validator;
	
	@BeforeAll
	static void setUp() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}
	
	@Test
	@DisplayName("Should not build with code")
	void shouldNotBuildWithCode() {
		
		// Given
		final RestBank restBank = new RestBank("123", "001", "BB");
		
		// When
		final Set<ConstraintViolation<RestBank>> violations = validator.validate(restBank);
		
		// Then
		Assertions.assertThat(violations).hasSize(1);
	}
	
	@Test
	@DisplayName("Should not build with less than 3 digits institution code")
	void shouldNotBuildWith2DigitsInstitutionCode() {
		
		// Given
		final RestBank restBank = new RestBank(null, "01", "BB");
		
		// When
		final Set<ConstraintViolation<RestBank>> violations = validator.validate(restBank);
		
		// Then
		Assertions.assertThat(violations).hasSize(1);
	}
	
	@Test
	@DisplayName("Should not build with more than 3 digits institution code")
	void shouldNotBuildWith4DigitsInstitutionCode() {
		
		// Given
		final RestBank restBank = new RestBank(null, "0001", "BB");
		
		// When
		final Set<ConstraintViolation<RestBank>> violations = validator.validate(restBank);
		
		// Then
		Assertions.assertThat(violations).hasSize(1);
	}
	
	@Test
	@DisplayName("Should not build with less than 2 digits name")
	void shouldNotBuildWith4DigitsName() {
		
		// Given
		final RestBank restBank = new RestBank(null, "001", "B");
		
		// When
		final Set<ConstraintViolation<RestBank>> violations = validator.validate(restBank);
		
		// Then
		Assertions.assertThat(violations).hasSize(1);
	}
	
	@Test
	@DisplayName("Should not build with more than 50 digits name")
	void shouldNotBuildWith51DigitsName() {
		
		// Given
		final RestBank restBank = new RestBank(null, "001", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		
		// When
		final Set<ConstraintViolation<RestBank>> violations = validator.validate(restBank);
		
		// Then
		Assertions.assertThat(violations).hasSize(1);
	}
}