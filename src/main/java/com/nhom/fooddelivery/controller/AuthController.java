package com.nhom.fooddelivery.controller;

import com.nhom.fooddelivery.constant.UserRole;
import com.nhom.fooddelivery.entity.Shop;
import com.nhom.fooddelivery.entity.User;
import com.nhom.fooddelivery.repository.ShopRepository;
import com.nhom.fooddelivery.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ShopRepository shopRepository;


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

    //đăng nhập
    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login"; // Trả về file WEB-INF/views/auth/login.jsp
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username,
                              @RequestParam String password,
                              HttpSession session,
                              Model model) {
        // 1. Tìm user trong DB
        User user = userRepo.findByUsername(username);

        // 2. Kiểm tra password (đang check thô, sau này dùng BCrypt)
        if (user != null && user.getPassword().equals(password)) {

            // 3. QUAN TRỌNG: Lưu user vào Session
            session.setAttribute("currentUser", user);

            // 4. Phân luồng chuyển hướng (Redirect) dựa trên Role
            UserRole role = user.getRole();

            if (role == UserRole.ADMIN) {
                return "redirect:/admin/dashboard"; // Admin vào trang quản trị
            } else if (role == UserRole.MERCHANT) {
                Shop shop = shopRepository.findByOwnerId(user.getId());
                if (shop != null) {
                    return "redirect:/shops/" + shop.getId();
                } else {
                    return "redirect:/shops/register"; // chưa có shop thì đi đăng ký
                }
            }
            else if (role == UserRole.SHIPPER) {
                return "redirect:/shipper/dashboard"; // Shipper vào xem đơn chờ
            } else {
                return "redirect:/"; // Khách hàng về trang chủ mua sắm
            }
        }

        // 5. Đăng nhập thất bại
        model.addAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
        return "auth/login";
    }

    // Hàm đăng xuất (xóa session)
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}
