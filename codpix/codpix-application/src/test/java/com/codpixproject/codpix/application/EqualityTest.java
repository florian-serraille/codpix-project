package com.codpixproject.codpix.application;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

public interface EqualityTest<T> {
	
	T createOne();
	
	T createAnother();
	
	@Test
	default void shouldBeEqual() {
		
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(createOne()).isEqualTo(createOne());
			softAssertions.assertThat(createOne().hashCode()).isEqualTo(createOne().hashCode());
		});
	}
	
	@Test
	default void shouldNotBeEqual() {
		
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(createOne()).isNotEqualTo(createAnother());
			softAssertions.assertThat(createOne().hashCode()).isNotEqualTo(createAnother().hashCode());
		});
	}
}
