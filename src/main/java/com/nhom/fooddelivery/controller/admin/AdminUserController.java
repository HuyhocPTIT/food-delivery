package com.nhom.fooddelivery.controller.admin;

import com.nhom.fooddelivery.constant.UserRole;
import com.nhom.fooddelivery.entity.User;
import com.nhom.fooddelivery.repository.FoodRepository;
import com.nhom.fooddelivery.repository.OrderRepository;
import com.nhom.fooddelivery.repository.ShopRepository;
import com.nhom.fooddelivery.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired private UserRepository userRepo;
    @Autowired private ShopRepository shopRepo;
    @Autowired private OrderRepository orderRepo;
    @Autowired private FoodRepository foodRepo;

    private boolean isAdmin(HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        return user != null && user.getRole() == UserRole.ADMIN;
    }

    // 2. Trang quản lý danh sách User
    @GetMapping
    public String listUsers(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if(currentUser==null ||  currentUser.getRole()!= UserRole.ADMIN){
            return "redirect:/login";
        }

        model.addAttribute("users",  userRepo.findAll());
        return "admin/users";
    }

    // Hàm thay đổi trạng thái (Khóa/Mở khóa) nhanh
    @GetMapping("/toggle-status/{id}")
    public String toggleUserStatus(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";

        User user = userRepo.findById(id).orElse(null);
        if (user != null) {
            // Ví dụ: Đổi qua lại giữa ACTIVED và BANNED
            if ("ACTIVED".equals(user.getStatus())) {
                user.setStatus("BANNED");
            } else {
                user.setStatus("ACTIVED");
            }
            userRepo.save(user);
        }
        return "redirect:/admin/users";
    }
}
