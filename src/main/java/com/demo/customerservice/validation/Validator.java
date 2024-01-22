package com.demo.customerservice.validation;

/**
 * Declare Validator method.
 *
 * @param <T> the type parameter
 */
public interface Validator<T> {


    /**
     * Validate.
     *
     * @param source the source
     */
    void validate(T source);

}
