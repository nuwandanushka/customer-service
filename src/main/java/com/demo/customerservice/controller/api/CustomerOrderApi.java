package com.demo.customerservice.controller.api;

import com.demo.customerservice.dto.CreateUpdateOrderRequest;
import com.demo.customerservice.dto.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Define Customer order apis.
 */
public interface CustomerOrderApi {

    /**
     * Create order API.
     *
     * @param createUpdateOrderRequest the create update order request
     * @return the order response
     */
    @Operation(
            summary = "Create customer order",
            description = "create new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            description = "Request data to create new order")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    OrderResponse createOrder(
            @RequestBody(required = true) CreateUpdateOrderRequest createUpdateOrderRequest);

}
