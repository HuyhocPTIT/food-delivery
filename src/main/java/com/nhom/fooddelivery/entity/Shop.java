package com.nhom.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "shops")
@Data
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String image;

    @OneToOne // Mỗi quán ăn thường gắn với một tài khoản chủ quán
    @JoinColumn(name = "owner_id")
    private User owner;
}