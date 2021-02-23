package com.codpix.application.configuration.bank;

import com.codpix.domain.bank.BankRegistration;
import com.codpix.domain.bank.BankRegistrationUseCase;
import com.codpix.domain.bank.BankRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BankConfiguration {
	
	@Bean
	public BankRegistration bankRegistration(final BankRepository bankRepository) {
		return new BankRegistrationUseCase(bankRepository);
	}
	
}
