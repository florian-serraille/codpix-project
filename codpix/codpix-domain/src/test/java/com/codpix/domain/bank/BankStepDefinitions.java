package com.codpix.domain.bank;

import com.codpix.domain.TestContext;
import com.codpix.domain.error.BankAlreadyRegisteredException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

public class BankStepDefinitions {
	
	private final TestContext testContext;
	private final BankRegistration bankRegistration;
	
	public BankStepDefinitions(final TestContext testContext) {
		this.testContext = testContext;
		this.bankRegistration = new BankRegistrationUseCase(new InMemoryBank());
	}
	
	
	@Given("The valid bank BB")
	public void theValidBankBb() {
		testContext.bankRegistrationRequest = new BankRegistrationRequest("001", "Banco do Brasil");
	}
	
	@When("Bank BB ask for registration")
	public void bankBbAskForRegistration() {
		
		try {
			testContext.bank = bankRegistration.register(testContext.bankRegistrationRequest);
		} catch (Exception exception){
			testContext.error = exception;
		}
	}
	
	@Then("Bank receive code")
	public void bankReceiveCode() {
		new BankAssert(testContext.bank).hasValidCode();
	}
	
	@Given("The valid bank BB is already registered")
	public void theValidBankBbIsAlreadyRegistered() {
		
		theValidBankBb();
		bankBbAskForRegistration();
	}
	
	@Then("Bank receive an error for for being already registered")
	public void bankReceiveAnErrorForForBeingAlreadyRegistered() {
		
		Assertions.assertThat(testContext.error)
		          .isNotNull()
		          .isExactlyInstanceOf(BankAlreadyRegisteredException.class)
		          .hasMessageContaining(testContext.bankRegistrationRequest.getInstitutionCode());
	}
}
