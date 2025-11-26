package com.kylelieber.trimlight.data;

public class TrimlightException extends RuntimeException {

  public TrimlightException(String message) {
    super(message);
  }

  public TrimlightException(String message, Throwable cause) {
    super(message, cause);
  }
}
