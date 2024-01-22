package com.demo.customerservice.client;

import com.demo.customerservice.dto.ProductResponse;
import com.demo.customerservice.service.impl.CustomerOrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * The type Product service client fallback.
 */
@Component
public class ProductServiceClientFallback implements ProductServiceClient {

    private final Logger logger = LoggerFactory.getLogger(ProductServiceClientFallback.class);

    @Override
    public ProductResponse getProductById(String id) {
        logger.error("Returning fallback message when service is not found");
        return new ProductResponse();
    }
}
