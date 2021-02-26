package com.codpixproject.codpix.application.entrypoint.error;

import ch.qos.logback.classic.Level;
import com.codpixproject.codpix.application.LogSpy;
import com.codpixproject.codpix.domain.error.ErrorStatus;
import com.codpixproject.codpix.domain.error.StandardMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Locale;

import static com.codpixproject.codpix.domain.error.CodPixException.builder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith({ MockitoExtension.class, LogSpy.class })
class CodPixErrorHandlerUnitTest {
	
	private static WebRequest request;
	
	@Mock
	private MessageSource messages;
	
	@InjectMocks
	private CodPixErrorHandler handler;
	
	private final LogSpy logs;
	
	public CodPixErrorHandlerUnitTest(LogSpy logs) {
		this.logs = logs;
	}
	
	@BeforeAll
	public static void loadUserLocale() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		request = new ServletWebRequest(new MockHttpServletRequest("GET", "http://localhost:8080/api/v1/bank"));
	}
	
	@Test
	public void shouldHandleAsServerErrorWithoutStatus() {
		
		ResponseEntity<CodPixError> response = handler.handleCodPixException(
				builder(StandardMessage.USER_MANDATORY).build(), request
		);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Test
	void shouldHandleAllErrorsAsUniqueHttpCode() {
		
		final var statuses = new HashSet<>();
		
		for (ErrorStatus status : ErrorStatus.values()) {
			final var response = handler.handleCodPixException(builder(StandardMessage.USER_MANDATORY)
					                                                   .status(status).build(), request);
			
			statuses.add(response.getStatusCode());
		}
		
		assertThat(statuses.size()).isEqualTo(ErrorStatus.values().length);
	}
	
	@Test
	public void shouldGetDefaultCodPixMessageWithoutMessageForBadRequest() {
		
		final var response = handler.handleCodPixException(
				builder(null).status(ErrorStatus.BAD_REQUEST).build(), request);
		
		assertThat(response.getBody().getErrorType()).isEqualTo("user.bad-request");
	}
	
	@Test
	public void shouldGetDefaultCodPixMessageWithoutMessageForServerError() {
		
		final var response = handler.handleCodPixException(builder(null).build(), request);
		
		assertThat(response.getBody().getErrorType()).isEqualTo("server.internal-server-error");
	}
	
	@Test
	public void shouldGetDefaultMessageForUnknownMessage() {
		
		when(messages.getMessage("codpix.error.hey", null, Locale.ENGLISH))
				.thenThrow(new NoSuchMessageException("hey"));
		
		when(messages.getMessage("codpix.error.server.internal-server-error", null, Locale.ENGLISH))
				.thenReturn("User message");
		
		final var response = handler.handleCodPixException(builder(() -> "hey").build(), request);
		
		final var body = response.getBody();
		assertThat(body.getErrorType()).isEqualTo("hey");
		assertThat(body.getMessage()).isEqualTo("User message");
	}
	
	@Test
	public void shouldHandleUserCodPixExceptionWithMessageWithoutArguments() {
		
		final var cause = new RuntimeException();
		final var exception = builder(StandardMessage.USER_MANDATORY)
				                      .cause(cause)
				                      .status(ErrorStatus.BAD_REQUEST)
				                      .message("Hum")
				                      .build();
		
		when(messages.getMessage("codpix.error.user.mandatory", null, Locale.ENGLISH))
				.thenReturn("User message");
		
		final var response = handler.handleCodPixException(exception, request);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		
		final var body = response.getBody();
		
		assertThat(body.getErrorType()).isEqualTo("user.mandatory");
		assertThat(body.getMessage()).isEqualTo("User message");
		assertThat(body.getFieldsErrors()).isNull();
	}
	
	@Test
	public void shouldReplaceArgumentsValueFromCodPixException() {
		
		when(messages.getMessage("codpix.error.user.mandatory", null, Locale.ENGLISH))
				.thenReturn("User {{ firstName }} {{ lastName}} message");
		
		final var response = handler.handleCodPixException(builder(StandardMessage.USER_MANDATORY)
				                                                   .argument("firstName", "Joe")
				                                                   .argument("lastName", "Dalton")
				                                                   .build(),
		                                                   request);
		
		assertThat(response.getBody().getMessage()).isEqualTo("User Joe Dalton message");
	}
	
	@Test
	public void shouldHandleMethodArgumentTypeMismatchException() {
		
		final var mismatchException = new MethodArgumentTypeMismatchException(null, null, null, null, null);
		
		final var response = handler.handleMethodArgumentTypeMismatchException(mismatchException, request);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getBody().getErrorType()).isEqualTo("user.bad-request");
	}
	
	@Test
	public void shouldHandleMethodArgumentTypeMismatchExceptionWithCodPixError() {
		
		final var cause = builder(StandardMessage.USER_MANDATORY).build();
		final var mismatchException = new MethodArgumentTypeMismatchException(null, null, null, null, cause);
		
		final var response = handler.handleMethodArgumentTypeMismatchException(mismatchException, request);
		
		assertThat(response.getBody().getErrorType()).isEqualTo("user.mandatory");
	}
	
	@Test
	public void shouldLogUserErrorAsWarn() {
		
		handler.handleCodPixException(builder(StandardMessage.BAD_REQUEST)
				                              .status(ErrorStatus.BAD_REQUEST)
				                              .message("error message")
				                              .build(),
		                              request);
		
		logs.assertLogged(Level.WARN, "error message");
	}
	
	// TODO: 2/25/2021 Uncomment when enable spring security
	//	@Test
	//	public void shouldLogAuthenticationExceptionAsDebug() {
	//
	//		handler.handleAuthenticationException(new InsufficientAuthenticationException("oops"), request);
	//
	//		logs.assertLogged(Level.DEBUG, "oops");
	//	}
	
	@Test
	public void shouldLogServerErrorAsError() {
		
		handler.handleCodPixException(builder(StandardMessage.INTERNAL_SERVER_ERROR)
				                              .status(ErrorStatus.INTERNAL_SERVER_ERROR)
				                              .message("error message")
				                              .build(),
		                              request);
		
		logs.assertLogged(Level.ERROR, "error message");
	}
	
	@Test
	public void shouldLogErrorResponseBody() {
		
		final var cause = new RestClientResponseException("error", 400, "status", null,
		                                                  "service error response".getBytes(),
		                                                  Charset.defaultCharset());
		
		handler.handleCodPixException(builder(StandardMessage.INTERNAL_SERVER_ERROR)
				                              .status(ErrorStatus.INTERNAL_SERVER_ERROR)
				                              .message("error message")
				                              .cause(cause)
				                              .build(),
		                              request);
		
		logs.assertLogged(Level.ERROR, "error message");
		logs.assertLogged(Level.ERROR, "service error response");
	}
}
