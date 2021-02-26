package com.codpixproject.codpix.domain.error;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Parent exception used in CodPix Application application. Those exceptions will be resolved as human readable errors.
 *
 * <p>
 * You can use this implementation directly:
 * </p>
 *
 * <p>
 * <code>
 *     <pre>
 *       CodPixException.builder(StandardMessages.USER_MANDATORY)
 *          .argument("key", "value")
 *          .argument("other", "test")
 *          .status(ErrorsHttpStatus.BAD_REQUEST)
 *          .message("Error message")
 *          .cause(new RuntimeException())
 *          .build();
 *     </pre>
 *   </code>
 * </p>
 *
 * <p>
 * Or make extension exceptions:
 * </p>
 *
 * <p>
 * <code>
 *     <pre>
 *       public class MissingMandatoryValueException extends CodPixException {
 *
 *         public MissingMandatoryValueException(CodPixMessage codPixMessage, String fieldName) {
 *           this(builder(codPixMessage, fieldName, defaultMessage(fieldName)));
 *         }
 *
 *         protected MissingMandatoryValueException(CodPixExceptionBuilder builder) {
 *           super(builder);
 *         }
 *
 *         private static CodPixExceptionBuilder builder(CodPixMessage codPixMessage, String fieldName, String message) {
 *           return CodPixException.builder(codPixMessage)
 *               .status(ErrorsHttpStatus.INTERNAL_SERVER_ERROR)
 *               .argument("field", fieldName)
 *               .message(message);
 *         }
 *
 *         private static String defaultMessage(String fieldName) {
 *           return "The field \"" + fieldName + "\" is mandatory and wasn't set";
 *         }
 *       }
 *     </pre>
 *   </code>
 * </p>
 */
public class CodPixException extends RuntimeException {
	
	private final Map<String, String> arguments;
	private final ErrorStatus status;
	private final CodPixMessage codPixMessage;
	
	protected CodPixException(CodPixExceptionBuilder builder) {
		super(getMessage(builder), getCause(builder));
		arguments = getArguments(builder);
		status = getStatus(builder);
		codPixMessage = getCodPixMessage(builder);
	}
	
	private static String getMessage(CodPixExceptionBuilder builder) {
		if (builder == null) {
			return null;
		}
		
		return builder.message;
	}
	
	private static Throwable getCause(CodPixExceptionBuilder builder) {
		if (builder == null) {
			return null;
		}
		
		return builder.cause;
	}
	
	private static Map<String, String> getArguments(CodPixExceptionBuilder builder) {
		if (builder == null) {
			return null;
		}
		
		return Collections.unmodifiableMap(builder.arguments);
	}
	
	private static ErrorStatus getStatus(CodPixExceptionBuilder builder) {
		if (builder == null) {
			return null;
		}
		
		return builder.status;
	}
	
	private static CodPixMessage getCodPixMessage(CodPixExceptionBuilder builder) {
		if (builder == null) {
			return null;
		}
		
		return builder.codpixMessage;
	}
	
	public static CodPixExceptionBuilder builder(CodPixMessage message) {
		return new CodPixExceptionBuilder(message);
	}
	
	public Map<String, String> getArguments() {
		return arguments;
	}
	
	public ErrorStatus getStatus() {
		return status;
	}
	
	public CodPixMessage getCodPixMessage() {
		return codPixMessage;
	}
	
	public static class CodPixExceptionBuilder {
		
		private final Map<String, String> arguments = new HashMap<>();
		private final CodPixMessage codpixMessage;
		
		private String message;
		private ErrorStatus status;
		private Throwable cause;
		
		public CodPixExceptionBuilder(CodPixMessage codpixMessage) {
			this.codpixMessage = codpixMessage;
		}
		
		public CodPixExceptionBuilder argument(String key, Object value) {
			arguments.put(key, getStringValue(value));
			
			return this;
		}
		
		private String getStringValue(Object value) {
			if (value == null) {
				return "null";
			}
			
			return value.toString();
		}
		
		public CodPixExceptionBuilder status(ErrorStatus status) {
			this.status = status;
			
			return this;
		}
		
		public CodPixExceptionBuilder message(String message) {
			this.message = message;
			
			return this;
		}
		
		public CodPixExceptionBuilder cause(Throwable cause) {
			this.cause = cause;
			
			return this;
		}
		
		public CodPixException build() {
			return new CodPixException(this);
		}
	}
}