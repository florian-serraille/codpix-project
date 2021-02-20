package com.codpix.domain.bank;

import com.codpix.domain.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BankStepDefinitions {
	
	private final TestContext testContext;
	private final BankRegistration bankRegistration;
	
	public BankStepDefinitions(final TestContext testContext) {
		this.testContext = testContext;
		this.bankRegistration = new BankRegistrationUseCase(new InMemoryBank());
	}
	
	@Given("A valid bank register request")
	public void aValidBankRegisterRequest() {
		testContext.bankRegistrationRequest = new BankRegistrationRequest("001", "Banco do Brasil");
	}
	
	@When("Bank ask for registration")
	public void bankAskForRegistration() {
		testContext.bank = bankRegistration.register(testContext.bankRegistrationRequest);
	}
	
	@Then("Bank receive code")
	public void bankReceiveCode() {
		new BankAssert(testContext.bank).hasValidCode();
	}
}
