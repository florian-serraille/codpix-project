package com.codpixproject.codpix.application.entrypoint.error;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

class QueryParameter {
  
  @Getter
  @Setter
  @Min(message = ValidationMessage.VALUE_TOO_LOW, value = 42)
  @Max(message = ValidationMessage.VALUE_TOO_HIGH, value = 42)
  private int parameter;
}
