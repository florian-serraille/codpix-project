package com.codpixproject.codpix.application.entrypoint.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.Pattern;

public class ComplicatedRequest {
  
  @Getter
  @Pattern(message = ValidationMessage.WRONG_FORMAT, regexp = "complicated")
  private final String value;

  public ComplicatedRequest(@JsonProperty("value") String value) {
    this.value = value;
  }

}
