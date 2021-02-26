package com.codpixproject.codpix.application.configuration.bank;

import com.codpixproject.codpix.domain.bank.BankRegistration;
import com.codpixproject.codpix.domain.bank.BankRegistrationUseCase;
import com.codpixproject.codpix.domain.bank.BankRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BankConfiguration {
	
	@Bean
	public BankRegistration bankRegistration(final BankRepository bankRepository) {
		return new BankRegistrationUseCase(bankRepository);
	}
	
}
