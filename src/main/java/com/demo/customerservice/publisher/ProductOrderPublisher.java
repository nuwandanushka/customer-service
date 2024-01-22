package com.demo.customerservice.publisher;

import com.demo.customerservice.dto.OrderResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductOrderPublisher {

    private final Logger logger = LoggerFactory.getLogger(ProductOrderPublisher.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void publish(String channel, OrderResponse orderResponse) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            String message = mapper.writeValueAsString(orderResponse);
            redisTemplate.convertAndSend(channel, message);
        } catch (Exception e) {
            logger.error("Error in publishing ProductOrder updates {}", e);
        }

    }
}
