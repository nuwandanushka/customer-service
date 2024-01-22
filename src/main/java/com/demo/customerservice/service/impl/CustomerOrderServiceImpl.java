package com.demo.customerservice.service.impl;

import com.demo.customerservice.client.ProductServiceClient;
import com.demo.customerservice.dto.CreateUpdateOrderRequest;
import com.demo.customerservice.dto.OrderResponse;
import com.demo.customerservice.dto.ProductResponse;
import com.demo.customerservice.entity.CustomerEntity;
import com.demo.customerservice.entity.OrderEntity;
import com.demo.customerservice.entity.OrderProductEntity;
import com.demo.customerservice.entity.enums.OrderProductStatus;
import com.demo.customerservice.entity.enums.OrderStatus;
import com.demo.customerservice.publisher.ProductOrderPublisher;
import com.demo.customerservice.repository.CustomerRepository;
import com.demo.customerservice.repository.OrderProductRepository;
import com.demo.customerservice.repository.OrderRepository;
import com.demo.customerservice.service.CustomerOrderService;
import com.demo.customerservice.validation.message.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

    private final Logger logger = LoggerFactory.getLogger(CustomerOrderServiceImpl.class);
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private ProductServiceClient client;

    @Autowired
    private ProductOrderPublisher productOrderPublisher;

    @Override
    public OrderResponse createOrder(CreateUpdateOrderRequest request) {

        logger.info("Start createOrder");

        Optional<CustomerEntity> customerEntityOptional
                = customerRepository.findById(UUID.fromString(request.getCustomerId()));

        Assert.isTrue(customerEntityOptional.isPresent(),
                String.format(Messages.CUSTOMER_IS_NOT_AVAILABLE, request.getCustomerId()));

        OrderResponse orderResponse = new OrderResponse();

        // create order record
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderNumber(UUID.randomUUID().toString());
        orderEntity.setCustomerId(customerEntityOptional.get());
        orderEntity.setOrderStatus(OrderStatus.PENDING_ORDER_ACCEPT.name());

        OrderEntity orderEntityCreated = orderRepository.save(orderEntity);

        orderResponse.setOrderNumber(orderEntity.getOrderNumber());
        orderResponse.setStatus(OrderStatus.PENDING_ORDER_ACCEPT.name());
        List<OrderResponse.ProductDto> productDtoList = new ArrayList<>();

        request.getList().stream().forEach(productDto -> {

            OrderResponse.ProductDto productResponseDto = orderResponse.new ProductDto();

            Optional<ProductResponse> response = Optional.ofNullable(client
                    .getProductById(productDto.getProductId()));

            OrderProductEntity orderProductEntity = null;

            // validate feasibility of purchasing product (check quantity available in store)
            if (response.isPresent()) {
                ProductResponse productResponse = response.get();
                orderProductEntity = new OrderProductEntity();
                orderProductEntity.setProductCode(productResponse.getId());

                if(productResponse.getQuantity() >= productDto.getQuantity()) {
                    orderProductEntity.setProductOrderStatus(OrderProductStatus.ACCEPTED.name());
                } else {
                    orderProductEntity.setProductOrderStatus(OrderProductStatus.INSUFFICIENT.name());
                }

                // create order product entity
                orderProductEntity.setQuantity(productDto.getQuantity());
                orderProductEntity.setOrderId(orderEntityCreated);
                orderProductRepository.save(orderProductEntity);

            } else {

                // create order product entity
                orderProductEntity = new OrderProductEntity();
                orderProductEntity.setProductCode(productDto.getProductId());
                orderProductEntity.setProductOrderStatus(OrderProductStatus.UNAVAILABLE.name());
                orderProductEntity.setQuantity(productDto.getQuantity());
                orderProductEntity.setOrderId(orderEntityCreated);
                orderProductRepository.save(orderProductEntity);


            }

            productResponseDto.setProductId(productDto.getProductId());
            productResponseDto.setQuantity(productDto.getQuantity());
            productResponseDto.setStatus(orderProductEntity.getProductOrderStatus());
            productDtoList.add(productResponseDto);

        });

        orderResponse.setList(productDtoList);

        // publish order request product service to update inventory
        productOrderPublisher.publish("productUpdateTopic", orderResponse);


        return orderResponse;
    }
}
