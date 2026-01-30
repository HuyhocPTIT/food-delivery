package com.nhom.fooddelivery.repository;
import com.nhom.fooddelivery.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Tìm các đơn hàng đang chờ Shipper đến nhận (Quán đã làm xong)
    List<Order> findByStatus(String status);

    // Tìm các đơn mà Shipper cụ thể đang đi giao
    List<Order> findByShipperIdAndStatus(Long shipperId, String status);

    // Thống kê: Đếm số đơn đã giao thành công của 1 Shipper
    Long countByShipperIdAndStatus(Long shipperId, String status);
}