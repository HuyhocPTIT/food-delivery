package com.nhom.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rating; // 1 đến 5 sao
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Ai là người đánh giá

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food; // Đánh giá cho món ăn nào

    private LocalDateTime createdAt = LocalDateTime.now();
}