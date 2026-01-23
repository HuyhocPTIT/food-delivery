package com.nhom.fooddelivery.repository;
import com.nhom.fooddelivery.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}