package com.clothify.ecommerce.exception.user;

public class UserPasswordAlreadyUsedException extends RuntimeException {
  public UserPasswordAlreadyUsedException(String message) {
    super(message);
  }
}
