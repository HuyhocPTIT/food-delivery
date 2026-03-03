package com.nhom.fooddelivery.controller;

import com.nhom.fooddelivery.entity.Food;
import com.nhom.fooddelivery.repository.FoodRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private FoodRepository foodRepository;

    @GetMapping("/")
    public String home(Model model, HttpSession session) {

        // ===== LAY DANH SACH MON AN =====
        List<Food> foodList = foodRepository.findAll();
        model.addAttribute("foods", foodList);

        // ===== TINH SO LUONG TRONG GIO HANG =====
        Map<Long, Integer> cart =
                (Map<Long, Integer>) session.getAttribute("cart");

        int cartCount = 0;
        if (cart != null) {
            for (int qty : cart.values()) {
                cartCount += qty;
            }
        }

        model.addAttribute("cartCount", cartCount);

        // Message test (co the xoa sau)
        model.addAttribute("message", "Connect Spring Boot and JSP was successful!");

        // ===== TRA VE TRANG CHU =====
        return "index"; // hoac "index" tuy JSP cua ban
    }
}
