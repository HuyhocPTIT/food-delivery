package com.nhom.fooddelivery.repository;

import com.nhom.fooddelivery.entity.Food;
import com.nhom.fooddelivery.entity.Shop;
import com.nhom.fooddelivery.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface   FoodRepository extends JpaRepository<Food, Long> {

    // Lấy tất cả món theo shop
    List<Food> findByShop(Shop shop);

    // Lấy tất cả món theo category
    List<Food> findByCategory(Category category);

    // Lấy món theo shop + category
    List<Food> findByShopAndCategory(Shop shop, Category category);

    // Tìm món theo tên
    List<Food> findByNameContainingIgnoreCase(String keyword);
}
