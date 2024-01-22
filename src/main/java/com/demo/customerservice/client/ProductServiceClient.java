package com.demo.customerservice.client;

import com.demo.customerservice.dto.ProductResponse;
import com.demo.customerservice.platform.common.RequestMapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Define Product service client.
 */
@FeignClient(name = "product-service", url = "${product.client.url}", fallback = ProductServiceClientFallback.class)
public interface ProductServiceClient {

    /**
     * Gets product by id.
     *
     * @param id the id
     * @return the product by id
     */
    @GetMapping(RequestMapper.BY_PRODUCT_ID)
    public ProductResponse getProductById(@PathVariable(value = "id") String id);
}
