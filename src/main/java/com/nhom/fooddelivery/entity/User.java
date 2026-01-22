package com.nhom.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users") // Spring sẽ tạo bảng tên 'users' trong MySQL
@Data // Tự tạo Getter, Setter, toString... nhờ thư viện Lombok
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tăng ID (1, 2, 3...)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;
    private String fullName;
    private String role; // Phân quyền: CUSTOMER, MERCHANT, SHIPPER, ADMIN
}