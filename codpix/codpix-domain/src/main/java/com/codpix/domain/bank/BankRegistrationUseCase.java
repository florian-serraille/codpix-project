package com.codpix.domain.bank;

import com.codpix.domain.error.BankAlreadyRegisteredException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BankRegistrationUseCase implements BankRegistration {
	
	private final BankRepository bankRepository;
	
	public Bank register(final BankRegistrationRequest bankRegistrationRequest) {
		
		final Bank bank = new Bank(bankRegistrationRequest);
		checkIfBankAlreadyExist(bankRegistrationRequest);
		bankRepository.register(bank);
		
		return bank;
	}
	
	private void checkIfBankAlreadyExist(final BankRegistrationRequest bankRegistrationRequest) {
		if (bankRepository.exist(bankRegistrationRequest.getInstitutionCode())) {
			throw BankAlreadyRegisteredException.forRequest(bankRegistrationRequest);
		}
	}
}