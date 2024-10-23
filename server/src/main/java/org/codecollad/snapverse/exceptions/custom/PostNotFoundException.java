package org.codecollad.snapverse.exceptions.custom;

public class PostNotFoundException extends RuntimeException {
  public PostNotFoundException(String message) {
    super(message);
  }
}
