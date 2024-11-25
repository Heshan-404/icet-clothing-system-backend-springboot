package com.clothify.ecommerce.exception.user;

public class UserEmailAlreadyRegisteredException extends RuntimeException {
  public UserEmailAlreadyRegisteredException(String message) {
    super(message);
  }
}
