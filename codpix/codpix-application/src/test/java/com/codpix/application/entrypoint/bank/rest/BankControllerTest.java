package com.codpix.application.entrypoint.bank.rest;

import com.codpix.domain.bank.Bank;
import com.codpix.domain.bank.BankRegistration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BankController.class)
class BankControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BankRegistration bankRegistration;
	
	@Test
	void shouldReturn201CodeForBankRequest() throws Exception {
		
		var restBank = new RestBank(null, "001", "BB");
		final var registrationRequest = restBank.toBankRegistrationRequest();
		
		Mockito.when(bankRegistration.register(registrationRequest))
		       .thenReturn(new Bank(registrationRequest));
		
		this.mockMvc.perform(post("/api/v1/bank").contentType(MediaType.APPLICATION_JSON)
		                                  .content(new ObjectMapper().writeValueAsString(restBank)))
		            .andDo(print())
		            .andExpect(status().isCreated())
		            .andExpect(header().string(LOCATION, startsWith("/api/v1/bank/")))
		            .andExpect(jsonPath("@.code", notNullValue()))
		            .andExpect(jsonPath("@.institutionCode", is(restBank.getInstitutionCode())))
		            .andExpect(jsonPath("@.name", is(restBank.getName())));
	}
	
}