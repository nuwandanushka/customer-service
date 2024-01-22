package com.demo.customerservice.controller;

import com.demo.customerservice.controller.api.CustomerApi;
import com.demo.customerservice.dto.CreateUpdateCustomerRequest;
import com.demo.customerservice.dto.CustomerDeleteResponse;
import com.demo.customerservice.dto.CustomerResponse;
import com.demo.customerservice.platform.common.RequestMapper;
import com.demo.customerservice.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RequestMapper.CUSTOMER)
public class CustomerController implements CustomerApi {

    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @Override
    public CustomerResponse createCustomer(CreateUpdateCustomerRequest createUpdateCustomerRequest) {
        logger.info("Starting createCustomer");
        CustomerResponse productResponse
                = customerService.createCustomer(createUpdateCustomerRequest);
        return productResponse;
    }

    @Override
    public CustomerResponse updateCustomer(String id, CreateUpdateCustomerRequest createUpdateCustomerRequest) {
        return null;
    }

    @Override
    public CustomerDeleteResponse deleteCustomer(String id) {
        return null;
    }

    @Override
    public CustomerResponse findCustomerById(String id) {
        return null;
    }
}
