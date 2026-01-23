package com.nhom.fooddelivery.repository;
import com.nhom.fooddelivery.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {
}