package com.demo.customerservice.dto;

import lombok.Data;

import java.util.List;

/**
 * The Create update order request.
 */
@Data
public class CreateUpdateOrderRequest {

    private String customerId;

    private List<ProductDto> list;

    /**
     * The type Product dto.
     */
    @Data
    public static class ProductDto {
        private String productId;
        private int quantity;
    }
}
