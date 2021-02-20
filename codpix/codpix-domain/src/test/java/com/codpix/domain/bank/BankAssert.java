package com.codpix.domain.bank;

import org.assertj.core.api.AbstractAssert;

import static java.util.Objects.nonNull;

public class BankAssert extends AbstractAssert<BankAssert, Bank> {
	
	public BankAssert(final Bank bank) {
		super(bank, BankAssert.class);
	}
	
	public BankAssert hasValidCode() {
		
		matches(bank -> nonNull(bank.getCode()), "Bank should have a non null id");
		return this;
	}
}
