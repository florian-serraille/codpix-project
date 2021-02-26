package com.codpixproject.codpix.application.entrypoint.error;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ArgumentsReplacerTest {
	
	@Test
	public void shouldNotReplaceArgumentsInNullMessage() {
		assertThat(ArgumentsReplacer.replaceArguments(null, Map.of("key", "value"))).isNull();
	}
	
	@Test
	public void shouldNotReplaceArgumentsWithoutArguments() {
		assertThat(ArgumentsReplacer.replaceArguments("Hey {{ user }}", null)).isEqualTo("Hey {{ user }}");
	}
	
	@Test
	public void shouldReplaceKnownArguments() {
		assertThat(ArgumentsReplacer.replaceArguments("Hey {{ user }}, how's {{ friend }} doing? Say {{user}}",
		                                              Map.of("user", "Joe")))
				.isEqualTo("Hey Joe, how's {{ friend }} doing? Say Joe");
	}
}
