package com.demo.customerservice.service;

import com.demo.customerservice.dto.CreateUpdateCustomerRequest;
import com.demo.customerservice.dto.CustomerResponse;

/**
 * The interface Customer service.
 */
public interface CustomerService {
    /**
     * Create customer customer response.
     *
     * @param createUpdateCustomerRequest the create update customer request
     * @return the customer response
     */
    CustomerResponse createCustomer(CreateUpdateCustomerRequest createUpdateCustomerRequest);
}
