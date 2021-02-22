package com.codpix.application.provider.bank.db;

import com.codpix.domain.bank.Bank;
import com.codpix.domain.bank.BankRegistrationRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class JpaBankTest {
	
	@Test
	void mappingToJpaBankTest() {
		
		// Given
		
		final var bank = new Bank(new BankRegistrationRequest("001", "banco do Brasil"));
		
		final var expectedJpaBank = new JpaBank();
		expectedJpaBank.setCode(bank.getCode());
		expectedJpaBank.setInstitutionCode(bank.getInstitutionCode());
		expectedJpaBank.setName(bank.getName());
		
		// When
		final var actualJpaBank = new JpaBank(bank);
		
		// Then
		Assertions.assertThat(actualJpaBank).isEqualToComparingFieldByField(expectedJpaBank);
		
	}
	
}