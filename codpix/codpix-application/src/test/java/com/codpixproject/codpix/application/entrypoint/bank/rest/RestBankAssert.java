package com.codpixproject.codpix.application.entrypoint.bank.rest;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.SoftAssertions;

import java.util.Objects;

class RestBankAssert extends AbstractAssert<RestBankAssert, RestBank> {
	
	RestBankAssert(final RestBank bank) {
		super(bank, RestBankAssert.class);
	}
	
	RestBankAssert isValidForRequest(final String institutionCode, final String name) {
		
		SoftAssertions.assertSoftly(softAssertions -> {
			matches(bank -> Objects.isNull(bank.getCode()), "RestBank should have a null code");
			matches(bank -> institutionCode.equals(bank.getInstitutionCode()),
			        String.format("Institution code should be %s but is actually %s", institutionCode,
			                      actual.getInstitutionCode()));
			matches(bank -> name.equals(bank.getName()),
			        String.format("Name should be %s but is actually %s", institutionCode, actual.getName()));
		});
		
		return this;
	}
	
	RestBankAssert isValidForResponse(final String code, final String institutionCode, final String name) {
		
		SoftAssertions.assertSoftly(softAssertions -> {
			matches(bank -> code.equals(bank.getCode()),
			        String.format("Code should be %s but is actually %s", code, actual.getCode()));
			matches(bank -> institutionCode.equals(bank.getInstitutionCode()),
			        String.format("Institution code should be %s but is actually %s", institutionCode,
			                      actual.getInstitutionCode()));
			matches(bank -> name.equals(bank.getName()),
			        String.format("Name should be %s but is actually %s", institutionCode, actual.getName()));
		});
		
		return this;
	}
}
