package com.demo.customerservice.service.impl;

import com.demo.customerservice.controller.CustomerController;
import com.demo.customerservice.dto.CreateUpdateCustomerRequest;
import com.demo.customerservice.dto.CustomerResponse;
import com.demo.customerservice.entity.CustomerEntity;
import com.demo.customerservice.publisher.ProductOrderPublisher;
import com.demo.customerservice.repository.CustomerRepository;
import com.demo.customerservice.service.CustomerService;
import com.demo.customerservice.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerRepository repository;

    @Autowired
    @Qualifier("customerCreateValidator")
    private Validator customerCreateValidator;

    @Autowired
    ProductOrderPublisher productOrderPublisher;

    @Override
    public CustomerResponse createCustomer(
            CreateUpdateCustomerRequest createUpdateCustomerRequest) {

        logger.info("Start createCustomer request {}", createUpdateCustomerRequest);

        customerCreateValidator.validate(createUpdateCustomerRequest);

        CustomerEntity customerEntity = new CustomerEntity();
        BeanUtils.copyProperties(createUpdateCustomerRequest, customerEntity);

        CustomerEntity customerEntityCreated = repository.save(customerEntity);

        logger.info("CreateCustomer successful");

        CustomerResponse response = new CustomerResponse();

        BeanUtils.copyProperties(customerEntityCreated, response);
        response.setId(customerEntityCreated.getCustomerId().toString());
        response.setCreatedDate(customerEntityCreated.getCreationDate());

        logger.info("End CreateCustomer response {}", response);

        return response;
    }
}
