package com.codpixproject.codpix.application.entrypoint.bank.rest;

import com.codpixproject.codpix.domain.bank.Bank;
import com.codpixproject.codpix.domain.bank.BankRegistrationRequest;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Getter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "bank", description = "bank participant")
public class RestBank {
	
	@Null
	@Schema(description = "Code assign by codpix", example = "4694-ddsa675-ds846-das1684")
	private final String code;
	@NotEmpty
	@Size(min = 3, max = 3)
	@Schema(description = "Institution code own by the bank", example = "001")
	private final String institutionCode;
	@NotEmpty
	@Size(min = 2, max = 50)
	@Schema(description = "Bank name", example = "Banco do Brasil")
	private final String name;
	
	@JsonCreator(mode = PROPERTIES)
	public RestBank(@JsonProperty("code") final String code,
	                @JsonProperty("institutionCode") final String institutionCode,
	                @JsonProperty("name") final String name) {
		
		this.code = code;
		this.institutionCode = institutionCode;
		this.name = name;
	}
	
	static RestBank fromBank(final Bank bank) {
		return new RestBank(bank.getCode().toString(), bank.getInstitutionCode(), bank.getName());
	}
	
	BankRegistrationRequest toBankRegistrationRequest() {
		return new BankRegistrationRequest(this.institutionCode, this.name);
	}
}
