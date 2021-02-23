package com.codpix.domain.bank;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class BankRegistrationRequest {
	
	private final String institutionCode;
	private final String name;
}
