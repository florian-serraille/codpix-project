package com.codpix.application.configuration.openapi;

import io.swagger.v3.oas.models.info.Contact;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "api.documentation")
public class ApiDocumentation {
	
	private String version;
	private String name;
	private String description;
	private ContactDetails contact;
	
	public Contact buildContact() {
		return contact.toContact();
	}
	
	@Getter
	@Setter
	public static class ContactDetails {
		
		private String email;
		private String name;
		private String url;
		
		Contact toContact() {
			
			final Contact contact = new Contact();
			contact.setName(name);
			contact.setEmail(email);
			contact.setUrl(url);
			
			return contact;
			
		}
	}
}
