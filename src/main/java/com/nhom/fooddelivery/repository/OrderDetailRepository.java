package com.nhom.fooddelivery.repository;
import com.nhom.fooddelivery.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}