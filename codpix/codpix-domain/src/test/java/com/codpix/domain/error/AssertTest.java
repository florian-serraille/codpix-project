package com.codpix.domain.error;

import org.junit.Test;

import static com.codpix.domain.error.Assert.field;
import static com.codpix.domain.error.Assert.notNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class AssertTest {
	
	@Test
	public void notNullAssertShouldFailForNullField() {
		
		// Given
		final Object fieldValue = null;
		final var fieldName = "field name";
		
		// When
		final Throwable throwable = catchThrowable(() -> notNull(fieldName, fieldValue));
		
		//Then
		assertThat(throwable).isNotNull()
		                     .isInstanceOf(MissingMandatoryValueException.class);
	}
	
	@Test
	public void notNullAssertShouldContainErrorInformation() {
		
		// Given
		final Object fieldValue = null;
		final var fieldName = "field name";
		
		// When
		final Throwable throwable = catchThrowable(() -> notNull(fieldName, fieldValue));
		
		//Then
		assertThat(throwable).isNotNull()
		                     .hasMessageContaining(fieldName);
	}
	
	@Test
	public void notNullAssertShouldSuccessForNotNullField() {
		
		// Given
		final var fieldValue = "not null field";
		final var fieldName = "field name";
		
		// When
		final Throwable throwable = catchThrowable(() -> notNull(fieldName, fieldValue));
		
		//Then
		assertThat(throwable).isNull();
	}
	
	@Test
	public void lengthAssertShouldFailForShorterValue() {
		
		// Given
		final var fieldValue = "123";
		final var fieldName = "field name";
		final var expectedSize = fieldValue.length() + 1;
		
		// Given
		final Throwable throwable = catchThrowable(() -> field(fieldName, fieldValue).length(expectedSize));
		
		// Then
		assertThat(throwable).isNotNull()
		                     .isExactlyInstanceOf(StringBadSizeException.class);
	}
	
	@Test
	public void lengthAssertShouldContainErrorInformationForShorterValue() {
		
		// Given
		final var fieldValue = "123";
		final var fieldName = "field name";
		final var expectedSize = fieldValue.length() + 1;
		
		// Given
		final Throwable throwable = catchThrowable(() -> field(fieldName, fieldValue).length(expectedSize));
		
		// Then
		assertThat(throwable).isNotNull()
		                     .hasMessageContaining(fieldName)
		                     .hasMessageContaining(String.valueOf(expectedSize))
		                     .hasMessageContaining(String.valueOf(fieldValue.length()));
	}
	
	@Test
	public void lengthAssertShouldFailForLongerValue() {
		
		// Given
		final var fieldValue = "123";
		final var fieldName = "field name";
		final var expectedSize = fieldValue.length() - 1;
		
		// Given
		final Throwable throwable = catchThrowable(() -> field(fieldName, fieldValue).length(expectedSize));
		
		// Then
		assertThat(throwable).isNotNull()
		                     .isExactlyInstanceOf(StringBadSizeException.class);
	}
	
	@Test
	public void lengthAssertShouldContainErrorInformationForLongerValue() {
		
		// Given
		final var fieldValue = "123";
		final var fieldName = "field name";
		final var expectedSize = fieldValue.length() - 1;
		
		// Given
		final Throwable throwable = catchThrowable(() -> field(fieldName, fieldValue).length(expectedSize));
		
		// Then
		assertThat(throwable).isNotNull()
		                     .hasMessageContaining(fieldName)
		                     .hasMessageContaining(String.valueOf(expectedSize))
		                     .hasMessageContaining(String.valueOf(fieldValue.length()));
	}
	
	@Test
	public void lengthAssertShouldPassForEqualLength() {
		
		// Given
		final var fieldValue = "123";
		final var fieldName = "field name";
		final var expectedSize = fieldValue.length();
		
		// Given
		final Throwable throwable = catchThrowable(() -> field(fieldName, fieldValue).length(expectedSize));
		
		// Then
		assertThat(throwable).isNull();
	}
	
	@Test
	public void maxLengthAssertShouldFailForLongerValue() {
		
		// Given
		final var fieldValue = "123";
		final var fieldName = "field name";
		final var maxSize = fieldValue.length() - 1;
		
		// Given
		final Throwable throwable = catchThrowable(() -> field(fieldName, fieldValue).maxlength(maxSize));
		
		// Then
		assertThat(throwable).isNotNull()
		                     .isExactlyInstanceOf(StringBadSizeException.class);
	}
	
	@Test
	public void maxLengthAssertShouldContainErrorInformationForLongerValue() {
		
		// Given
		final var fieldValue = "123";
		final var fieldName = "field name";
		final var maxSize = fieldValue.length() - 1;
		
		// Given
		final Throwable throwable = catchThrowable(() -> field(fieldName, fieldValue).maxlength(maxSize));
		
		// Then
		assertThat(throwable).isNotNull()
		                     .hasMessageContaining(fieldName)
		                     .hasMessageContaining(String.valueOf(maxSize))
		                     .hasMessageContaining(String.valueOf(fieldValue.length()));
	}
	
	@Test
	public void maxLengthAssertShouldPassForEqualLength() {
		
		// Given
		final var fieldValue = "123";
		final var fieldName = "field name";
		final var maxSize = fieldValue.length();
		
		// Given
		final Throwable throwable = catchThrowable(() -> field(fieldName, fieldValue).maxlength(maxSize));
		
		// Then
		assertThat(throwable).isNull();
	}
	
	@Test
	public void maxLengthAssertShouldPassForShorterLength() {
		
		// Given
		final var fieldValue = "123";
		final var fieldName = "field name";
		final var maxSize = fieldValue.length() + 1;
		
		// Given
		final Throwable throwable = catchThrowable(() -> field(fieldName, fieldValue).maxlength(maxSize));
		
		// Then
		assertThat(throwable).isNull();
	}
	
	@Test
	public void notBlankAssertShouldFail() {
		
		// Given
		final var fieldValue = "";
		final var fieldName = "field name";
		
		// Given
		final Throwable throwable = catchThrowable(() -> field(fieldName, fieldValue).notBlank());
		
		// Then
		assertThat(throwable).isNotNull()
		                     .isExactlyInstanceOf(MissingMandatoryValueException.class);
	}
	
	@Test
	public void notBlankAssertShouldContainErrorInformation() {
		
		// Given
		final var fieldValue = "";
		final var fieldName = "field name";
		
		// Given
		final Throwable throwable = catchThrowable(() -> field(fieldName, fieldValue).notBlank());
		
		// Then
		assertThat(throwable).isNotNull()
		                     .hasMessageContaining(fieldName);
	}
	
	@Test
	public void notBlankAssertShouldPass() {
		
		// Given
		final var fieldValue = "non blank field";
		final var fieldName = "field name";
		
		// Given
		final Throwable throwable = catchThrowable(() -> field(fieldName, fieldValue).notBlank());
		
		// Then
		assertThat(throwable).isNull();
	}
}