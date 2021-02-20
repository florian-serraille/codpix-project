package com.codpix.domain.bank;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BankRegistrationRequest {
	
	private final String institutionCode;
	private final String name;
}
