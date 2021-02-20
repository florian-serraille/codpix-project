package com.codpix.domain.bank;

import com.codpix.domain.bank.Bank;
import com.codpix.domain.bank.BankRepository;

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
}
