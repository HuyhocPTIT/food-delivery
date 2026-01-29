package com.nhom.fooddelivery.controller;

import com.nhom.fooddelivery.constant.UserRole;
import com.nhom.fooddelivery.entity.User;
import com.nhom.fooddelivery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    @Autowired
    private UserRepository userRepo;

    // lấy ra form đăng kí
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    // đăng ký
    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        if(userRepo.findByUsername(user.getUsername())!=null){
            model.addAttribute("error", "Tên người dùng đã tồn tại!");
            return "auth/register";
        }

        user.setRole(UserRole.CUSTOMER); // mặc định khi đăng kí là customer
        userRepo.save(user);
        return "redirect:/login";
    }
}
