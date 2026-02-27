package com.nhom.fooddelivery.repository;
import com.nhom.fooddelivery.entity.Shop;
import com.nhom.fooddelivery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface ShopRepository extends JpaRepository<Shop, Long> {

    // Tìm shop theo tên (search)
    List<Shop> findByNameContainingIgnoreCase(String name);

    // Lấy shop theo owner
    // Tìm Shop dựa trên người sở hữu
    Shop findByOwnerId(Long ownerId);

    // Kiểm tra owner đã có shop chưa
    boolean existsByOwner(User owner);

    List<Shop> findByStatus(String status);
}