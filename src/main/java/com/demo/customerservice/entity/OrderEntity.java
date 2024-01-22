package com.demo.customerservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Define Order entity.
 */
@Entity
@Table(name = "orders")
@Data
public class OrderEntity extends Auditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "order_number")
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "customer_fk"))
    private CustomerEntity customerId;

    @Column(name = "order_status")
    private String orderStatus;

}
