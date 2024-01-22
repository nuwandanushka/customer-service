package com.demo.customerservice.controller;

import com.demo.customerservice.controller.api.CustomerOrderApi;
import com.demo.customerservice.dto.CreateUpdateOrderRequest;
import com.demo.customerservice.dto.OrderResponse;
import com.demo.customerservice.platform.common.RequestMapper;
import com.demo.customerservice.service.CustomerOrderService;
import com.demo.customerservice.service.impl.CustomerOrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RequestMapper.CUSTOMER_ORDER)
public class CustomerOrderController implements CustomerOrderApi {

    private final Logger logger = LoggerFactory.getLogger(CustomerOrderController.class);

    @Autowired
    private CustomerOrderService customerOrderService;


    @Override
    public OrderResponse createOrder(CreateUpdateOrderRequest createUpdateOrderRequest) {
        logger.info("start createOrder request {}", createUpdateOrderRequest);
        return customerOrderService.createOrder(createUpdateOrderRequest);
    }
}
