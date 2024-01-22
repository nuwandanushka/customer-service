package com.demo.customerservice.dto;

import lombok.Data;

import java.util.List;

/**
 * Define Order response.
 */
@Data
public class OrderResponse {

    private String orderNumber;

    private List<ProductDto> list;

    private String status;

    /**
     * The type Product dto.
     */
    @Data
    public class ProductDto {
        private String productId;
        private int quantity;
        private String status;
    }
}
