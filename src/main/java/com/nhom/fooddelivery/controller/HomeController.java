package com.nhom.fooddelivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Đánh dấu đây là một Controller để Spring nhận diện
public class HomeController {

    // Khi người dùng truy cập địa chỉ http://localhost:8080/hello
    @GetMapping("/hello")
    public String welcome(Model model) {

        // Gửi một biến tên là "message" sang giao diện JSP
        model.addAttribute("message", "Connect Spring Boot and JSP was successful!");

        // Trả về tên file JSP nằm trong thư mục /WEB-INF/jsp/
        // Bạn chỉ cần viết "welcome", Spring sẽ tự hiểu là file "welcome.jsp"
        return "welcome";
    }
}