package com.nhom.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "shops")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String image; // image này là logo của shop

    private String status = "PENDING"; // Mặc định khi tạo mới là chờ duyệt

    @OneToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    // Quan hệ song hướng với Food
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Food> foods;

    @OneToMany(mappedBy = "shop")
    private List<Order> orders;



}