package com.codpixproject.codpix.application.entrypoint.bank.rest;

import com.codpixproject.codpix.domain.bank.Bank;
import com.codpixproject.codpix.domain.bank.BankRegistration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
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
	void aValidRegistrationBankRequestShouldReturn201WithLocationHeaderAndProvideBankCode() throws Exception {
		
		// Given
		final var uri = "/api/v1/bank/";
		final var restBank = new RestBank(null, "001", "BB");
		final var registrationRequest = restBank.toBankRegistrationRequest();
		
		// When Then
		Mockito.when(bankRegistration.register(registrationRequest))
		       .thenReturn(new Bank(registrationRequest));
		
		this.mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON)
		                                         .content(new ObjectMapper().writeValueAsString(restBank)))
		            .andDo(print())
		            .andExpect(status().isCreated())
		            .andExpect(header().string(LOCATION, startsWith(uri)))
		            .andExpect(jsonPath("$.code", notNullValue()))
		            .andExpect(jsonPath("$.institutionCode", is(restBank.getInstitutionCode())))
		            .andExpect(jsonPath("$.name", is(restBank.getName())));
	}
	
	@Test
	void anInvalidRegistrationBankRequestShouldReturn400() throws Exception {
		
		// Given
		final var uri = "/api/v1/bank";
		final var restBank = new RestBank("123", "001", "BB");
		final var registrationRequest = restBank.toBankRegistrationRequest();
		
		// When Then
		this.mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON)
		                              .content(new ObjectMapper().writeValueAsString(restBank)))
		            .andDo(print())
		            .andExpect(status().isBadRequest())
		            .andExpect(jsonPath("@.codeDescription", is("Bad Request")))
		            .andExpect(jsonPath("@.errorType", is("user.bad-request")))
		            .andExpect(jsonPath("@.message", is("Provided values are incorrects.")))
		            .andExpect(jsonPath("@.path", is(uri)))
		            .andExpect(jsonPath("@.timestamp", notNullValue()))
		            .andExpect(jsonPath("@.fieldsErrors", hasSize(1)))
		            .andExpect(jsonPath("@.fieldsErrors[0].fieldPath", is("code")))
		            .andExpect(jsonPath("@.fieldsErrors[0].reason", is("user.must-be-null")))
		            .andExpect(jsonPath("@.fieldsErrors[0].message", is("The field must be null.")));
		
		Mockito.verifyNoInteractions(bankRegistration);
	}
}