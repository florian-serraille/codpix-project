package com.codpix.domain.bank;

public interface BankRepository {
	
	void register(Bank bank);
	
	boolean exist(String institutionCode);
}
