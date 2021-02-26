package com.codpixproject.codpix.application;

import com.codpixproject.codpix.application.entrypoint.bank.rest.BankController;
import com.codpixproject.codpix.domain.bank.Bank;
import com.codpixproject.codpix.domain.bank.BankRegistration;
import com.codpixproject.codpix.domain.bank.BankRegistrationRequest;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class ContractBaseClass {
	
	@Autowired
	private BankController bankController;
	
	@MockBean
	private BankRegistration bankRegistration;
	
	@BeforeEach
	public void setup() {
		
		RestAssuredMockMvc.standaloneSetup(bankController);
		
		Mockito.when(bankRegistration.register(any(BankRegistrationRequest.class)))
		       .thenReturn(new Bank(new BankRegistrationRequest("001", "Banco do Brasil")));
	}
	
}
