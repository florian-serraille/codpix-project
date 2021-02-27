package com.codpixproject.codpix.domain.bank;

import org.assertj.core.api.AbstractAssert;

import static java.util.Objects.nonNull;

class BankAssert extends AbstractAssert<BankAssert, Bank> {
	
	BankAssert(final Bank bank) {
		super(bank, BankAssert.class);
	}
	
	BankAssert hasValidCode() {
		
		matches(bank -> nonNull(bank.getCode()), "Bank should have a non null id");
		return this;
	}
}
