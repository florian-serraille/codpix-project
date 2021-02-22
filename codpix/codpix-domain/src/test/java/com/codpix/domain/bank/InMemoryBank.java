package com.codpix.domain.bank;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryBank implements BankRepository {
	
	private final Map<UUID, Bank> repository;
	
	public InMemoryBank() {
		this.repository = new ConcurrentHashMap<>();
	}
	
	@Override
	public void register(final Bank bank) {
		repository.put(bank.getCode(), bank);
	}
	
	@Override
	public boolean exist(final String institutionCode) {
		return repository.values().stream().anyMatch(bank -> bank.getInstitutionCode().equals(institutionCode));
	}
}
