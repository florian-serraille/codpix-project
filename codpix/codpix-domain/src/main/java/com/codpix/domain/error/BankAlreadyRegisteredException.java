package com.codpix.domain.error;

import com.codpix.domain.bank.BankRegistrationRequest;

public class BankAlreadyRegisteredException extends CodPixException {
	
	public BankAlreadyRegisteredException(final BankRegistrationRequest request) {
		super(String.format("A bank already exist for institution code %s", request.getInstitutionCode()));
	}
}
