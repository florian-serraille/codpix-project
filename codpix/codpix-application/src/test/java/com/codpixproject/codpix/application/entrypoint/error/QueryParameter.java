package com.codpixproject.codpix.application.entrypoint.error;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import static com.codpixproject.codpix.application.entrypoint.error.ApplicationMessage.*;
import static com.codpixproject.codpix.application.entrypoint.error.ApplicationMessage.ValidationMessage.*;

class QueryParameter {
  
  @Getter
  @Setter
  @Min(message = VALUE_TOO_LOW, value = 42)
  @Max(message = VALUE_TOO_HIGH, value = 42)
  private int parameter;
}
