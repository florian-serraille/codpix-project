package com.codpixproject.codpix.application.configuration;

import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;

public class CodPixDisplayNameGenerator extends DisplayNameGenerator.Standard {
	
	@Override
	public String generateDisplayNameForClass(final Class<?> testClass) {
		return replaceCamelCase(super.generateDisplayNameForClass(testClass));
	}
	
	@Override
	public String generateDisplayNameForNestedClass(final Class<?> nestedClass) {
		return replaceCamelCase(super.generateDisplayNameForNestedClass(nestedClass));
	}
	
	@Override
	public String generateDisplayNameForMethod(final Class<?> testClass, final Method testMethod) {
		return replaceCamelCase(testMethod.getName());
	}
	
	private String replaceCamelCase(String camelCase) {
		
		var result = new StringBuilder();
		result.append(Character.toUpperCase(camelCase.charAt(0)));
		
		for (int i=1; i<camelCase.length(); i++) {
			if (Character.isUpperCase(camelCase.charAt(i))) {
				result.append(' ');
				result.append(Character.toLowerCase(camelCase.charAt(i)));
			} else {
				result.append(camelCase.charAt(i));
			}
		}
		return result.toString();
	}
}
