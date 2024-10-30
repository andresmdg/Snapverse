package org.codecollad.snapverse.exceptions.custom.post;

public class PostNotFoundException extends RuntimeException {
  public PostNotFoundException(String message) {
    super(message);
  }
}
