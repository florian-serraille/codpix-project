package com.codpixproject.codpix.application.entrypoint.error;

import lombok.Getter;

public class NotDeserializable {
  
  @Getter
  private final String value;

  public NotDeserializable(String value) {
    this.value = value;
  }

}
