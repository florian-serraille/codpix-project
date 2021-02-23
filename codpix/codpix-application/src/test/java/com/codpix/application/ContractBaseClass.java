package com.codpix.application;

import com.codpix.application.entrypoint.bank.rest.BankController;
import com.codpix.domain.bank.Bank;
import com.codpix.domain.bank.BankRegistration;
import com.codpix.domain.bank.BankRegistrationRequest;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class ContractBaseClass {
	
	@LocalServerPort
	private int port;
	
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
