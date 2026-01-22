package com.nhom.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "foods")
@Data
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private String description;
    private String image; // Lưu đường dẫn hoặc tên file ảnh
}