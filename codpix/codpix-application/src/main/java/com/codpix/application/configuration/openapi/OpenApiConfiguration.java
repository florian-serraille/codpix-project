package com.codpix.application.configuration.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class OpenApiConfiguration {
	
	private final ApiDocumentation doc;
	
	@Bean
	public OpenAPI openAPI() {
		return new OpenApiDescriptor(doc).getOpenAPI();
	}
	
	@Bean
	public GroupedOpenApi inventoryOpenApi() {
		
		final String[] paths = { "/api/v1/**" };
		return GroupedOpenApi.builder().group("codpix").pathsToMatch(paths)
		                     .build();
	}
	
	static class OpenApiDescriptor {
		
		@Getter
		private final OpenAPI openAPI;
		
		public OpenApiDescriptor(final ApiDocumentation doc) {
			
			this.openAPI = new OpenAPI()
					               .components(new Components())
					               .info(new Info().title(doc.getName())
					                               .version(doc.getVersion())
					                               .contact(doc.buildContact())
					                               .description(doc.getDescription())
					                               .license(new License().name("Apache 2.0")
					                                                     .url("http://springdoc.org")));
		}
	}
}
