package com.codpixproject.codpix.application.entrypoint.error;

import com.codpixproject.codpix.domain.error.CodPixException;
import com.codpixproject.codpix.domain.error.ErrorStatus;
import com.codpixproject.codpix.domain.error.StandardMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class CodPixErrorHandler extends ResponseEntityExceptionHandler {
	
	private static final String MESSAGE_PREFIX = "codpix.error.";
	private static final String DEFAULT_KEY = StandardMessage.INTERNAL_SERVER_ERROR.getMessageKey();
	private static final String BAD_REQUEST_KEY = StandardMessage.BAD_REQUEST.getMessageKey();
	private static final String STATUS_EXCEPTION_KEY = "status-exception";
	
	private final MessageSource messages;
	
	public CodPixErrorHandler(MessageSource messages) {
		
		Locale.setDefault(Locale.ENGLISH);
		this.messages = messages;
	}
	
	@ExceptionHandler(CodPixException.class)
	public ResponseEntity<CodPixError> handleCodPixException(final CodPixException exception,
	                                                         final WebRequest request) {
		final var status = getStatus(exception);
		
		logError(exception, status);
		
		final var messageKey = getMessageKey(status, exception);
		
		final var error = new CodPixError(status.getReasonPhrase(),
		                                  messageKey,
		                                  getMessage(messageKey, exception.getArguments()),
		                                  getRequestURI(request),
		                                  ZonedDateTime.now());
		return new ResponseEntity<>(error, status);
	}
	
	private String getRequestURI(final WebRequest request) {
		
		final var swr = (ServletWebRequest) request;
		return swr.getRequest().getRequestURI();
	}
	
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<CodPixError> handleResponseStatusException(final ResponseStatusException exception,
	                                                                 final WebRequest request) {
		
		final var status = exception.getStatus();
		logError(exception, status);
		
		final var error = new CodPixError(status.getReasonPhrase(),
		                                  STATUS_EXCEPTION_KEY,
		                                  buildErrorStatusMessage(exception),
		                                  getRequestURI(request),
		                                  ZonedDateTime.now());
		
		return new ResponseEntity<>(error, status);
	}
	
	private String buildErrorStatusMessage(ResponseStatusException exception) {
		
		var reason = exception.getReason();
		
		if (StringUtils.isBlank(reason)) {
			final var statusArgument = Map.of("status", String.valueOf(exception.getStatus().value()));
			
			return getMessage(STATUS_EXCEPTION_KEY, statusArgument);
		}
		
		return reason;
	}
	
	// TODO: 2/25/2021 Uncomment when enable spring security
	//	@ExceptionHandler(AccessDeniedException.class)
	//	public ResponseEntity<CodPixError> handleAccessDeniedException(final AccessDeniedException exception,
	//	                                                               final WebRequest request) {
	//		
	//		final var status = HttpStatus.FORBIDDEN;
	//		final var error = new CodPixError(status.getReasonPhrase(),
	//                                        "user.access-denied",
	//		                                  getMessage("user.access-denied", null),
	//		                                  getRequestURI(request),
	//		                                  ZonedDateTime.now());
	//		
	//		return new ResponseEntity<>(error, status);
	//	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<CodPixError> handleMethodArgumentTypeMismatchException(
			final MethodArgumentTypeMismatchException exception, final WebRequest request) {
		
		final var rootCause = ExceptionUtils.getRootCause(exception);
		final var status = HttpStatus.BAD_REQUEST;
		
		if (rootCause instanceof CodPixException) {
			return handleCodPixException((CodPixException) rootCause, request);
		}
		
		final var error = new CodPixError(status.getReasonPhrase(), BAD_REQUEST_KEY, getMessage(BAD_REQUEST_KEY, null),
		                                  getRequestURI(request), ZonedDateTime.now());
		
		return new ResponseEntity<>(error, status);
	}
	
	private HttpStatus getStatus(CodPixException exception) {
		ErrorStatus status = exception.getStatus();
		if (status == null) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		switch (status) {
			case BAD_REQUEST:
				return HttpStatus.BAD_REQUEST;
			// TODO: 2/25/2021 Uncomment when enable spring security
			//			case UNAUTHORIZED:
			//				return HttpStatus.UNAUTHORIZED;
			//			case FORBIDDEN:
			//				return HttpStatus.FORBIDDEN;
			case NOT_FOUND:
				return HttpStatus.NOT_FOUND;
			case CONFLICT:
				return HttpStatus.CONFLICT;
			default:
				return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}
	
	private void logError(Exception exception, HttpStatus status) {
		if (status.is5xxServerError()) {
			log.error("A server error was sent to a user: {}", exception.getMessage(), exception);
			
			logErrorBody(exception);
		} else {
			log.warn("An error was sent to a user: {}", exception.getMessage(), exception);
		}
	}
	
	private void logErrorBody(Exception exception) {
		Throwable cause = exception.getCause();
		if (cause instanceof RestClientResponseException) {
			RestClientResponseException restCause = (RestClientResponseException) cause;
			
			log.error("Cause body: {}", restCause.getResponseBodyAsString());
		}
	}
	
	private String getMessageKey(HttpStatus status, CodPixException exception) {
		if (exception.getCodPixMessage() == null) {
			return getDefaultMessage(status);
		}
		
		return exception.getCodPixMessage().getMessageKey();
	}
	
	private String getDefaultMessage(HttpStatus status) {
		if (status.is5xxServerError()) {
			return DEFAULT_KEY;
		}
		
		return BAD_REQUEST_KEY;
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception,
	                                                              final HttpHeaders headers,
	                                                              final HttpStatus status,
	                                                              final WebRequest request) {
		
		log.debug("Bean validation error {}", exception.getMessage(), exception);
		final var myStatus = HttpStatus.BAD_REQUEST;
		
		final var error = new CodPixError(myStatus.getReasonPhrase(), BAD_REQUEST_KEY,
		                                  getMessage(BAD_REQUEST_KEY, null),
		                                  getRequestURI(request), ZonedDateTime.now(), getFieldsError(exception));
		
		return new ResponseEntity<>(error, myStatus);
	}
	
	@ExceptionHandler
	public ResponseEntity<CodPixError> handleBeanValidationError(final ConstraintViolationException exception,
	                                                             final WebRequest request) {
		
		log.debug("Bean validation error {}", exception.getMessage(), exception);
		
		final var status = HttpStatus.BAD_REQUEST;
		final var error = new CodPixError(status.getReasonPhrase(), BAD_REQUEST_KEY, getMessage(BAD_REQUEST_KEY, null),
		                                  getRequestURI(request), ZonedDateTime.now(), getFieldsErrors(exception));
		
		return new ResponseEntity<>(error, status);
	}
	
	@Override
	protected ResponseEntity<Object> handleBindException(final BindException exception,
	                                                     final HttpHeaders headers,
	                                                     final HttpStatus status,
	                                                     final WebRequest request) {
		
		final var myStatus = HttpStatus.BAD_REQUEST;
		
		final var fieldErrors = exception.getFieldErrors().stream().map(toCodPixFieldError()).collect(
				Collectors.toList());
		
		final var error = new CodPixError(myStatus.getReasonPhrase(), BAD_REQUEST_KEY,
		                                  getMessage(BAD_REQUEST_KEY, null),
		                                  getRequestURI(request), ZonedDateTime.now(), fieldErrors);
		
		return new ResponseEntity<>(error, myStatus);
	}
	
	private ConstraintViolation<?> extractSource(final FieldError error) {
		
		return error.unwrap(ConstraintViolation.class);
	}
	
	private String getMessage(final String messageKey, final Map<String, String> arguments) {
		
		final var text = getMessageFromSource(messageKey);
		return ArgumentsReplacer.replaceArguments(text, arguments);
	}
	
	private String  getMessageFromSource(final String messageKey) {
		
		final var locale = LocaleContextHolder.getLocale();
		
		try {
			return messages.getMessage(MESSAGE_PREFIX + messageKey, null, locale);
			
		} catch (NoSuchMessageException e) {
			return messages.getMessage(MESSAGE_PREFIX + DEFAULT_KEY, null, locale);
		}
	}
	
	private List<CodPixFieldError> getFieldsErrors(final ConstraintViolationException exception) {
		return exception.getConstraintViolations().stream().map(toFieldError()).collect(Collectors.toList());
	}
	
	private List<CodPixFieldError> getFieldsError(final MethodArgumentNotValidException exception) {
		
		return exception.getBindingResult()
		                .getFieldErrors()
		                .stream()
		                .map(toCodPixFieldError())
		                .collect(Collectors.toList());
	}
	
	private Function<FieldError, CodPixFieldError> toCodPixFieldError() {
		
		return erro -> new CodPixFieldError(erro.getField(),
		                                    erro.getDefaultMessage(),
		                                    getMessage(erro.getDefaultMessage(), buildArguments(extractSource(erro))));
	}
	
	private Map<String, String> buildArguments(final ConstraintViolation<?> violation) {
		
		return violation.getConstraintDescriptor()
		                .getAttributes()
		                .entrySet()
		                .stream()
		                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().toString()));
	}
	
	private Function<ConstraintViolation<?>, CodPixFieldError> toFieldError() {
		
		return violation -> {
			
			final var arguments = buildArguments(violation);
			final var message = violation.getMessage();
			return new CodPixFieldError(violation.getPropertyPath().toString(), message,
			                            getMessage(message, arguments));
		};
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException exception,
	                                                              final HttpHeaders headers,
	                                                              final HttpStatus status,
	                                                              final WebRequest request) {
		
		log.error("Error reading query information: {}", exception.getMessage(), exception);
		final var myStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		final var error = new CodPixError(myStatus.getReasonPhrase(), DEFAULT_KEY, getMessage(DEFAULT_KEY, null),
		                                  getRequestURI(request), ZonedDateTime.now());
		
		return new ResponseEntity<>(error, myStatus);
	}
	
	// TODO: 2/25/2021 Uncomment when enable spring security
	//	@ExceptionHandler
	//	public ResponseEntity<CodPixError> handleAuthenticationException(final AuthenticationException exception,
	//	                                                                 final WebRequest request) {
	//
	//		log.debug("A user tried to do an unauthorized operation: {}", exception.getMessage(), exception);
	//		final var status = HttpStatus.BAD_REQUEST;
	//		final var message = AuthenticationMessage.NOT_AUTHENTICATED.getMessageKey();
	//		final var error = new CodPixError(status.getReasonPhrase(), getMessage(message, null), getRequestURI(request),
	//		                                  ZonedDateTime.now());
	//
	//		return new ResponseEntity<>(error, status);
	//	}
	
	@ExceptionHandler
	public ResponseEntity<CodPixError> handleRuntimeException(final Throwable throwable, final WebRequest request) {
		
		log.error("An unhandled error occurs: {}", throwable.getMessage(), throwable);
		final var status = HttpStatus.INTERNAL_SERVER_ERROR;
		final var error = new CodPixError(status.getReasonPhrase(), DEFAULT_KEY, getMessage(DEFAULT_KEY, null),
		                                  getRequestURI(request), ZonedDateTime.now());
		
		return new ResponseEntity<>(error, status);
	}
}
