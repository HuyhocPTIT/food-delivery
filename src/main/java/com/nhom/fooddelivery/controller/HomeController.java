package com.nhom.fooddelivery.controller;

import com.nhom.fooddelivery.entity.Food;
import com.nhom.fooddelivery.repository.FoodRepository; // Đảm bảo bạn đã tạo Interface này
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    // 1. Tiêm Repository để thao tác với DB
    @Autowired
    private FoodRepository foodRepository;

    @GetMapping("/")
    public String index(Model model) {
        // 2. Lấy tất cả món ăn từ bảng 'foods'
        List<Food> foodList = foodRepository.findAll();

        // 3. Đẩy danh sách này sang JSP với tên biến là "foods"
        model.addAttribute("foods", foodList);

        // Giữ lại message cũ của bạn để test kết nối
        model.addAttribute("message", "Connect Spring Boot and JSP was successful!");

        return "index";
    }
}