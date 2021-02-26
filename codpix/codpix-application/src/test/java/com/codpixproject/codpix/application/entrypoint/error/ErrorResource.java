package com.codpixproject.codpix.application.entrypoint.error;

import com.codpixproject.codpix.domain.error.CodPixException;
import com.codpixproject.codpix.domain.error.ErrorStatus;
import com.codpixproject.codpix.domain.error.StandardMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Pattern;

/**
 * Resource to expose errors endpoints
 */
@Validated
@RestController
@RequestMapping("/errors")
class ErrorResource {
	
	private static final Logger logger = LoggerFactory.getLogger(ErrorResource.class);
	
	@GetMapping("/runtime-exceptions")
	public void runtimeException() {
		throw new RuntimeException();
	}
	
	// TODO: 2/25/2021 Uncomment when enable spring security
	//  @GetMapping("/access-denied")
	//  public void accessDeniedException() {
	//    throw new AccessDeniedException("You shall not pass!");
	//  }
	
	@PostMapping("/codpix-exceptions")
	public void codPixException() {
		throw CodPixException
				      .builder(StandardMessage.INTERNAL_SERVER_ERROR)
				      .cause(new RuntimeException())
				      .status(ErrorStatus.INTERNAL_SERVER_ERROR)
				      .message("Oops")
				      .argument("key", "value")
				      .build();
	}
	
	@PostMapping("/responsestatus-with-message-exceptions")
	public void responseStatusWithMessageException() {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "oops");
	}
	
	@PostMapping("/responsestatus-without-message-exceptions")
	public void responseStatusWithoutMessageException() {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping
	public void queryStringWithRangedValue(@Validated QueryParameter parameter) {
	}
	
	@GetMapping("/{complicated}")
	public void complicatedArg(
			@Validated @Pattern(message = ValidationMessage.WRONG_FORMAT, regexp = "complicated")
			@PathVariable("complicated") String complicated
	) {
		logger.info("Congratulations you got it right!");
	}
	
	@PostMapping("/oops")
	public void complicatedBody(@Validated @RequestBody ComplicatedRequest request) {
		logger.info("You got it right!");
	}
	
	@PostMapping("/not-deserializables")
	public void notDeserializable(@RequestBody NotDeserializable request) {
	}
}
