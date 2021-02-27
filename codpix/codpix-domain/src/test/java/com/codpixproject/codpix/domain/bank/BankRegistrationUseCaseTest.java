package com.codpixproject.codpix.domain.bank;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BankRegistrationUseCaseTest {
	
	private BankRegistrationUseCase bankRegistration;
	
	@Mock
	private InMemoryBank bankRepository;
	
	@Before
	public void setUp() {
		bankRegistration = new BankRegistrationUseCase(bankRepository);
	}
	
	@Test
	public void shouldCallRepositoryWhenRegisteringValidBank() {
		
		// Given
		final var request = new BankRegistrationRequest("001", "Banco do Brasil");
		
		// When
		final var bank = bankRegistration.register(request);
		
		//Then
		Mockito.verify(bankRepository, Mockito.times(1)).register(bank);
	}
	
	@Test
	public void shouldNotRegisterBankForInvalidBankRequest() {
		
		// Given
		final var request = new BankRegistrationRequest("01", "Banco do Brasil");
		
		// When
		Assertions.catchThrowable(() -> bankRegistration.register(request));
		
		//Then
		Mockito.verifyZeroInteractions(bankRepository);
	}
}