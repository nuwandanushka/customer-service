package com.demo.customerservice.exception;


/**
 * Define Resource not found exception.
 */
public class ResourceNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * Parameterized constructor to initialize description.
   *
   * @param description String
   */
  public ResourceNotFoundException(String description) {
    super(description);
  }
}
