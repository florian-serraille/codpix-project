package com.codpix.application.entrypoint.bank.rest;

import com.codpix.domain.bank.BankRegistration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bank")
@Tag(name = "Bank management", description = "Bank registration into codpix")
public class BankController {
	
	private final BankRegistration bankRegistration;
	
	@PostMapping(consumes = APPLICATION_JSON_VALUE)
	@Operation(summary = "Bank registration", description = "Registers a bank and assigns it a code")
	@ApiResponse(description = "Created", responseCode = "201")
	public ResponseEntity<RestBank> registerBank(@Valid @RequestBody RestBank restBank) {
		
		var bank = bankRegistration.register(restBank.toBankRegistrationRequest());
		var registeredBank = RestBank.fromBank(bank);
		var location = Link.of("/api/v1/bank/" + registeredBank.getCode()).toUri();
		
		return ResponseEntity.created(location).body(registeredBank);
	}
	
}
