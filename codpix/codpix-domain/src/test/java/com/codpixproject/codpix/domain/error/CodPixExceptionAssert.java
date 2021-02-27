package com.codpixproject.codpix.domain.error;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class CodPixExceptionAssert extends AbstractAssert<CodPixExceptionAssert, CodPixException> {
	
	private CodPixExceptionAssert(final CodPixException exception) {
		super(exception, CodPixExceptionAssert.class);
	}
	
	public static CodPixExceptionAssert assertThat(final Throwable throwable) {
		
		Assertions.assertThat(throwable)
		          .isNotNull()
		          .isInstanceOf(CodPixException.class);
		return new CodPixExceptionAssert((CodPixException) throwable);
	}
	
	public CodPixExceptionAssert hasArgument(final String key, final String expectedValue) {
		
		Assertions.assertThat(actual.getArguments())
		          .withFailMessage("CodPixException does not have argument for key '%s'", key)
		          .containsKey(key);
		
		final var actualValue = this.actual.getArguments().get(key);
		
		Assertions.assertThat(actual.getArguments().get(key))
		          .withFailMessage("CodPixException argument for key '%s' has actual value '%s' and " +
		                           "does not match with expected value '%s'",
		                           key, actualValue, expectedValue)
		          .isEqualTo(expectedValue);
		
		return this;
	}
	
	public CodPixExceptionAssert hasStatus(final ErrorStatus expectedStatus) {
		
		Assertions.assertThat(actual.getStatus())
		          .withFailMessage("CodPixException status is '%s' but does not with expected '%s'",
		                           actual.getStatus(), expectedStatus)
		          .isEqualTo(expectedStatus);
		
		return this;
	}
	
	public void hasMessageContaining(final String message) {
		Assertions.assertThat(actual).hasMessageContaining(message);
	}
	
	public static class BankAlreadyRegisteredExceptionAssert extends CodPixExceptionAssert {
		
		public BankAlreadyRegisteredExceptionAssert(final BankAlreadyRegisteredException e) {
			super(e);
		}
		
		public static BankAlreadyRegisteredExceptionAssert assertThat(final Throwable throwable) {
			
			Assertions.assertThat(throwable)
			          .isNotNull()
			          .isExactlyInstanceOf(BankAlreadyRegisteredException.class);
			
			return new BankAlreadyRegisteredExceptionAssert((BankAlreadyRegisteredException) throwable);
		}
		
		public void isValid(final String message) {
			hasStatus(ErrorStatus.INTERNAL_SERVER_ERROR).hasArgument("institutionCode", message);
		}
	}
}