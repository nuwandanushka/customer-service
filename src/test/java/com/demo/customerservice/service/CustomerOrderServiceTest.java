package com.demo.customerservice.service;

import com.demo.customerservice.BaseCustomerServiceApplicationTest;
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
import com.demo.customerservice.service.impl.CustomerOrderServiceImpl;
import com.demo.customerservice.validation.message.Messages;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

public class CustomerOrderServiceTest extends BaseCustomerServiceApplicationTest {
    @InjectMocks
    private CustomerOrderServiceImpl service;
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductServiceClient productServiceClient;

    @Mock
    private ProductOrderPublisher productOrderPublisher;

    @Mock
    private OrderProductRepository orderProductRepository;


    @Test
    public void createOrderTest() {

        CreateUpdateOrderRequest createUpdateOrderRequest = new CreateUpdateOrderRequest();
        createUpdateOrderRequest.setCustomerId(getCustomerId().toString());

        CreateUpdateOrderRequest.ProductDto productDto = new CreateUpdateOrderRequest.ProductDto();

        productDto.setProductId(getProductId().toString());
        productDto.setQuantity(1);

        createUpdateOrderRequest.setList(Arrays.asList(productDto));

        Optional<CustomerEntity> customerEntityOptional = getCustomerEntity();

        when(customerRepository.findById(any())).thenReturn(customerEntityOptional);

        when(orderRepository.save(any())).thenReturn(getOrderEntity(customerEntityOptional));

        when(productServiceClient.getProductById(any())).thenReturn(getProductResponse());

        when(orderProductRepository.save(any())).thenReturn(getOrderProduct(customerEntityOptional));

        doNothing().when(productOrderPublisher).publish(any(), any());

        OrderResponse orderResponse = service.createOrder(createUpdateOrderRequest);

        Assertions.assertThat(orderResponse).isNotNull();

    }
    @Test
    public void createOrderTest_InvalidCustomerId() {

        CreateUpdateOrderRequest createUpdateOrderRequest = new CreateUpdateOrderRequest();
        createUpdateOrderRequest.setCustomerId(getCustomerId().toString());

        CreateUpdateOrderRequest.ProductDto productDto = new CreateUpdateOrderRequest.ProductDto();

        productDto.setProductId(getProductId().toString());
        productDto.setQuantity(1);

        createUpdateOrderRequest.setList(Arrays.asList(productDto));

        Optional<CustomerEntity> customerEntityOptional = getCustomerEntity();

        when(customerRepository.findById(any())).thenReturn(Optional.empty());

        lenient().when(orderRepository.save(any())).thenReturn(getOrderEntity(customerEntityOptional));

        lenient().when(productServiceClient.getProductById(any())).thenReturn(getProductResponse());

        lenient().when(orderProductRepository.save(any())).thenReturn(getOrderProduct(customerEntityOptional));

        lenient().doNothing().when(productOrderPublisher).publish(any(), any());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.createOrder(createUpdateOrderRequest);
        });

        assertEquals(String.format(Messages.CUSTOMER_IS_NOT_AVAILABLE, getCustomerId().toString()), exception.getMessage());
    }

    private OrderProductEntity getOrderProduct(Optional<CustomerEntity> customerEntityOptional) {
        OrderProductEntity orderProductEntity = new OrderProductEntity();
        orderProductEntity.setProductCode(getProductId().toString());
        orderProductEntity.setProductOrderStatus(OrderProductStatus.ACCEPTED.name());
        orderProductEntity.setQuantity(1);
        orderProductEntity.setOrderId(getOrderEntity(customerEntityOptional));
        return orderProductEntity;
    }

    private ProductResponse getProductResponse() {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductType("BOOK");
        productResponse.setDescription("Art Book");
        productResponse.setPrice(10.0);
        productResponse.setQuantity(4);
        productResponse.setCreatedBy("systemUser");
        productResponse.setCreatedDate(new Date());
        productResponse.setId(getProductId().toString());
        return productResponse;
    }

    public UUID getCustomerId() {
        return UUID.fromString("981e2c35-07e8-4385-96f7-34b121fb34ec");
    }

    public UUID getOrderId() {
        return UUID.fromString("14dfeffc-f37a-4b38-844c-7255cd002bce");
    }

    public UUID getProductId() {
        return UUID.fromString("251460c6-1e6f-486e-9fd3-411e4c1d4276");
    }

    private OrderEntity getOrderEntity(Optional<CustomerEntity> customerEntityOptional) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderNumber(getOrderId().toString());
        orderEntity.setCustomerId(customerEntityOptional.get());
        orderEntity.setOrderStatus(OrderStatus.PENDING_ORDER_ACCEPT.name());
        return orderEntity;
    }

    private Optional<CustomerEntity> getCustomerEntity() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCustomerId(getCustomerId());
        customerEntity.setEmail("nuwand.wickramasooriya@gmail.com");
        customerEntity.setFirstName("Nuwan");
        customerEntity.setLastName("Wick");
        customerEntity.setMobileNum("1234567");

        return Optional.ofNullable(customerEntity);
    }
}
