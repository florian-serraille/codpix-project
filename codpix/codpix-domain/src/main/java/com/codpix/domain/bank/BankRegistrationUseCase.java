package com.codpix.domain.bank;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class BankRegistrationUseCase implements BankRegistration {
	
	private final BankRepository bankRepository;
	
	public Bank register(final BankRegistrationRequest bankRegistrationRequest) {
		
		final Bank bank = new Bank(bankRegistrationRequest);
		bankRepository.register(bank);
		
		return bank;
	}
}