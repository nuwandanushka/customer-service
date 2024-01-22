package com.demo.customerservice.service;

import com.demo.customerservice.dto.CreateUpdateOrderRequest;
import com.demo.customerservice.dto.OrderResponse;

/**
 * The interface Customer order service.
 */
public interface CustomerOrderService {

    /**
     * Create order.
     *
     * @param request the request
     * @return the order response
     */
    OrderResponse createOrder(CreateUpdateOrderRequest request);
}
