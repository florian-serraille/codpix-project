package com.codpixproject.codpix.application.entrypoint.error;

import com.codpixproject.codpix.domain.error.CodPixMessage;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ErrorMessagesTest {
	
	private static final Set<Class<? extends CodPixMessage>> errors = getSubTypesOf();
	
	private static Set<Class<? extends CodPixMessage>> getSubTypesOf() {
		
		return new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage("com.codpixproject"))
		                                                 .setScanners(new SubTypesScanner())
		                                                 .filterInputsBy(new FilterBuilder().includePackage(
				                                                 "com.codpixproject"))
		).getSubTypesOf(CodPixMessage.class);
	}
	
	@Test
	public void shouldHaveOnlyEnumImplementations() {
		
		errors.forEach(error -> assertThat(error.isEnum() || error.isInterface())
				                        .as("Implementations of " + CodPixMessage.class.getName() +
				                            " must be enums and " + error.getName() + " wasn't")
				                        .isTrue()
		);
	}
	
	@Test
	public void shouldHaveMessagesForAllKeys() {
		
		final var messages = loadMessages();
		
		errors.stream()
		      .filter(Class::isEnum)
		      .forEach(error -> Arrays.stream(error.getEnumConstants())
		                              .forEach(value -> messages.forEach(assertMessageExist(value))));
	}
	
	private List<Properties> loadMessages() {
		
		try {
			return Files.list(Paths.get("src/main/resources/i18n")).map(toProperties()).collect(Collectors.toList());
			
		} catch (IOException e) {
			throw new AssertionError();
		}
	}
	
	private Function<Path, Properties> toProperties() {
		
		return file -> {
			
			try (InputStream input = Files.newInputStream(file)) {
				
				final var properties = new Properties();
				properties.load(input);
				return properties;
				
			} catch (IOException e) {
				throw new AssertionError();
			}
		};
	}
	
	private Consumer<Properties> assertMessageExist(CodPixMessage value) {
		
		return currentMessages -> assertThat(currentMessages.getProperty("codpix.error." + value.getMessageKey()))
				                          .as("Can't find message for " + value.getMessageKey() +
				                              " in all files, check your configuration")
				                          .isNotBlank();
	}
}
