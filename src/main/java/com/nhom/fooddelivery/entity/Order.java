package com.nhom.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Người đặt hàng
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    // Quán ăn nhận đơn
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    // Shipper giao hàng (Lúc mới đặt thì cột này sẽ NULL)
    @ManyToOne
    @JoinColumn(name = "shipper_id")
    private User shipper;

    private Double totalPrice;
    private String address;
    private String status; // PENDING, PREPARING, SHIPPING, DELIVERED, CANCELLED
    private LocalDateTime createdAt = LocalDateTime.now();
}