package com.codpixproject.codpix.application.provider.bank.db;

import com.codpixproject.codpix.domain.bank.Bank;
import com.codpixproject.codpix.domain.bank.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BankRepositoryAdapter implements BankRepository {
	
	private final JpaBankRepository repository;
	
	@Override
	public void register(final Bank bank) {
		repository.save(new JpaBank(bank));
	}
	
	@Override
	public boolean exist(final String institutionCode) {
		return repository.existsByInstitutionCode(institutionCode);
	}
}
