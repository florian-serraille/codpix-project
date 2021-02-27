package com.codpixproject.codpix.domain.bank;

import com.codpixproject.codpix.domain.error.Assert;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Bank {
	
	private final UUID code;
	private final String institutionCode;
	private final String name;
	
	public Bank(final BankRegistrationRequest bankRegistrationRequest) {
		
		final String bankInstitutionCode = bankRegistrationRequest.getInstitutionCode();
		Assert.field("institutionCode", bankInstitutionCode).notBlank().length(3);
		this.institutionCode = bankInstitutionCode;
		
		final String bankName = bankRegistrationRequest.getName();
		Assert.field("name", bankName).notBlank().minLength(2).maxlength(50);
		this.name = bankName;
		
		this.code = UUID.randomUUID();
	}
}
