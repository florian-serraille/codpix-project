package com.codpixproject.codpix.domain.error;

import com.codpixproject.codpix.domain.bank.BankRegistrationRequest;

public class BankAlreadyRegisteredException extends CodPixException {
	
	private BankAlreadyRegisteredException(final String message, final String institutionCode) {
		
		super(CodPixException.builder(StandardMessage.BANK_ALREADY_REGISTERED)
		                     .argument("institutionCode", institutionCode)
		                     .status(ErrorStatus.BAD_REQUEST)
		                     .message(message));
	}
	
	public static BankAlreadyRegisteredException forRequest(final BankRegistrationRequest request) {
		
		final String message = String.format("A bank already exist for institution code %s",
		                                     request.getInstitutionCode());
		
		return new BankAlreadyRegisteredException(message, request.getInstitutionCode());
	}
}
