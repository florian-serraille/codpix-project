package com.codpixproject.codpix.application.entrypoint.bank.rest;

import com.codpixproject.codpix.application.EqualityTest;
import com.codpixproject.codpix.application.entrypoint.TestJson;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.Validation;
import javax.validation.Validator;

class RestBankTest implements EqualityTest<RestBank> {
	
	private static Validator validator;
	
	@BeforeAll
	static void setUp() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}
	
	@Test
	void shouldBuild() {
		
		final var institutionCode = "001";
		final var name = "BB";
		
		new RestBankAssert(new RestBank(null, institutionCode, name)).isValidForRequest(institutionCode, name);
	}
	
	@Test
	void shouldNotBuildWhenCodeIsProvided() {
		
		// Given
		final var restBank = new RestBank("123", "001", "BB");
		
		// When
		final var violations = validator.validate(restBank);
		
		// Then
		Assertions.assertThat(violations).hasSize(1);
	}
	
	@ParameterizedTest(name = "for value {0}")
	@NullSource
	@ValueSource(strings = { "", " ", "\t", "01", "0001" })
	void shouldNotBuildWhenInstitutionIsInvalid(String institutionCode) {
		
		// Given
		final var restBank = new RestBank(null, institutionCode, "BB");
		
		// When
		final var violations = validator.validate(restBank);
		
		// Then
		Assertions.assertThat(violations.size()).isGreaterThanOrEqualTo(1);
	}
	
	@ParameterizedTest(name = "for value {0}")
	@NullSource
	@ValueSource(strings = { "", " ", "\t", "A", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" })
	void shouldNotBuildWhenNameIsInvalid(String name) {
		
		// Given
		final var restBank = new RestBank(null, "001", name);
		
		// When
		final var violations = validator.validate(restBank);
		
		// Then
		Assertions.assertThat(violations.size()).isGreaterThanOrEqualTo(1);
	}
	
	@Test
	void shouldSerializeAllFields() {
		
		// Given
		final var restBank = new RestBank(null, "001", "BB");
		
		// When
		final var json = TestJson.writeAsString(restBank);
		
		// Then
		Assertions.assertThat(json).isEqualTo("{\"institutionCode\":\"001\",\"name\":\"BB\"}");
	}
	
	@Test
	void shouldDeserializeAllFields() {
		
		// Given
		final var json = "{\"code\":\"123456\",\"institutionCode\":\"001\",\"name\":\"BB\"}";
		
		// When
		final var restBank = TestJson.readFromJson(json, RestBank.class);
		
		// Then
		new RestBankAssert(restBank).isValidForResponse("123456", "001", "BB");
		
	}
	
	@Override
	public RestBank createOne() {
		return new RestBank(null, "001", "BB");
	}
	
	@Override
	public RestBank createAnother() {
		return new RestBank(null, "756", "BANCOOB");
	}
}