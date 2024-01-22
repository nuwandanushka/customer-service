package com.demo.customerservice.validation;

import com.demo.customerservice.dto.CreateUpdateCustomerRequest;
import org.springframework.stereotype.Service;

/**
 * Define Customer create validator.
 */
@Service("customerCreateValidator")
public class CustomerCreateValidator implements Validator<CreateUpdateCustomerRequest> {
    @Override
    public void validate(CreateUpdateCustomerRequest source) {
        // validation for customer create

    }
}
