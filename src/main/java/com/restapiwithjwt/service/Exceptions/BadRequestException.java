package com.restapiwithjwt.service.Exceptions;

import static java.lang.String.format;

public class BadRequestException extends RuntimeException {
  private static final String MESSAGE_REQUEST =
          "Request failed: %s";

  public BadRequestException(String reason) {
    super(format(MESSAGE_REQUEST, reason));
  }
}
