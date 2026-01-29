package com.nhom.fooddelivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Connect Spring Boot and JSP was successful!");

        // Trả về tên file JSP nằm trong thư mục /WEB-INF/jsp/
        return "index";
    }
}