package com.nhom.fooddelivery.controller.admin;

import com.nhom.fooddelivery.constant.UserRole;
import com.nhom.fooddelivery.entity.User;
import com.nhom.fooddelivery.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired private UserRepository userRepo;
    @Autowired private ShopRepository shopRepo;
    @Autowired private OrderRepository orderRepo;
    @Autowired private FoodRepository foodRepo;

    // 1. Trang Dashboard tổng quan
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if(currentUser==null ||  currentUser.getRole()!=UserRole.ADMIN){
            return "redirect:/login";
        }

        // Đẩy số liệu thống kê thực tế từ DB ra UI
        model.addAttribute("totalUsers",  userRepo.count());
        model.addAttribute("totalShops",  shopRepo.count());
        model.addAttribute("totalFoods",  foodRepo.count());
        model.addAttribute("totalOrders",  orderRepo.count());

        return "admin/dashboard";
    }
}
