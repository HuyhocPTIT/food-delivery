package com.nhom.fooddelivery.repository;
import com.nhom.fooddelivery.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}