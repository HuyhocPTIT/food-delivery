package com.nhom.fooddelivery.controller.admin;

import com.nhom.fooddelivery.constant.UserRole;
import com.nhom.fooddelivery.entity.Shop;
import com.nhom.fooddelivery.entity.User;
import com.nhom.fooddelivery.repository.ShopRepository;
import com.nhom.fooddelivery.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


@Controller
@RequestMapping("/admin/shops")
public class AdminShopController {
    @Autowired private ShopRepository shopRepository;
    @Autowired private UserRepository userRepository;

    private boolean isAdmin(HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        return user != null && user.getRole() == UserRole.ADMIN;
    }

    // HIỂN THỊ TẤT CẢ SHOP
    @GetMapping
    public String listShops(Model model, HttpSession session){
        if(!isAdmin(session)){
            return "redirect:/login";
        }

        model.addAttribute("shops", shopRepository.findAll());
        return "admin/shop-list";
    }

    //DUYỆT SHOP
    // Chuyển trạng thái từ PENDING -> ACTIVE
    @GetMapping("/approve/{id}")
    public String approveShop(@PathVariable Long id, HttpSession session){
        if(!isAdmin(session)){
            return "redirect:/login";
        }

        Shop shop = shopRepository.findById(id).orElse(null);
        if(shop != null && shop.getStatus().equals("PENDING")){
            shop.setStatus("ACTIVE");
            shopRepository.save(shop);

            // duyệt user lên merchant
            User owner = shop.getOwner();
            owner.setRole(UserRole.MERCHANT);
            owner.setStatus("ACTIVED");
            userRepository.save(owner);
        }

        return "redirect:/admin/shops";
    }

    // XÓA SHOP
    @GetMapping("/delete/{id}")
    public String deleteShop(@PathVariable Long id, HttpSession session){
        if(!isAdmin(session)){
            return "redirect:/login";
        }

        Shop shop = shopRepository.findById(id).orElse(null);
        shop.setStatus("BANNED");

        return "redirect:/admin/shops";
    }

    // SỬA SHOP
    @GetMapping("/edit/{id}")
    public String editShop(@PathVariable Long id, HttpSession session, Model model){
        if(!isAdmin(session)){
            return "redirect:/login";
        }

        Shop shop = shopRepository.findById(id).orElse(null);
        model.addAttribute("shop", shop);
        return "admin/shop-form";
    }

    @PostMapping("/update")
    public String updateShop(@ModelAttribute Shop shop, HttpSession session, @RequestParam("imageFile") MultipartFile imageFile){
        if(!isAdmin(session)){
            return "redirect:/login";
        }

        Shop existingShop = shopRepository.findById(shop.getId()).orElse(null);

        if(existingShop != null) {
            existingShop.setName(shop.getName());
            existingShop.setAddress(shop.getAddress());
            existingShop.setImage(shop.getImage());
            if (!imageFile.isEmpty()) {
                try {
                    // --- Cấu hình đường dẫn lưu file ---
                    // Lưu vào thư mục tĩnh của dự án để hiển thị được ngay
                    String uploadDir = "src/main/resources/static/images/";
                    Path uploadPath = Paths.get(uploadDir);

                    // Tạo thư mục nếu chưa tồn tại
                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }

                    // --- Sinh tên file duy nhất (tránh bị trùng tên) ---
                    // Ví dụ: avatar.jpg -> 550e8400-e29b..._avatar.jpg
                    String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();

                    // --- Lưu file vào ổ cứng ---
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                    // --- Cập nhật đường dẫn trong DB (đường dẫn web) ---
                    // Lưu ý: Đường dẫn trong DB bắt đầu bằng /images/...
                    existingShop.setImage("/images/shop/" + fileName);

                } catch (IOException e) {
                    e.printStackTrace();
                    // Có thể thêm thông báo lỗi vào RedirectAttributes nếu muốn chuyên nghiệp hơn
                }
            }

            shopRepository.save(existingShop);
        }
        return "redirect:/admin/shops";
    }
}
