package com.codpixproject.codpix.application.entrypoint.error;

public final class ValidationMessage {
  public static final String MANDATORY = "user.mandatory";
  public static final String WRONG_FORMAT = "user.wrong-format";
  public static final String RPPS_FORMAT = "user.wrong-national-id-format";
  public static final String MAIL_FORMAT = "user.wrong-mail-format";
  public static final String VALUE_TOO_LOW = "user.too-low";
  public static final String VALUE_TOO_HIGH = "user.too-high";

  private ValidationMessage() {}
}
