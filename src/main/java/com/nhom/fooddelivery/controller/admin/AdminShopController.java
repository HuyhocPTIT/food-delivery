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

            // duyệt user lên merchant
            User owner = shop.getOwner();
            if(owner != null){
                owner.setRole(UserRole.MERCHANT);
                owner.setStatus("ACTIVED");
                userRepository.save(owner);
            }

            shopRepository.save(shop);
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

        shopRepository.save(shop);

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
    public String updateShop(@ModelAttribute Shop shop, @RequestParam("imageFile") MultipartFile imageFile, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        Shop existingShop = shopRepository.findById(shop.getId()).orElse(null);

        if (existingShop != null) {
            // 1. Cập nhật thông tin text
            existingShop.setName(shop.getName());
            existingShop.setAddress(shop.getAddress());
            existingShop.setStatus(shop.getStatus());

            // 2. Xử lý ảnh: Chỉ cập nhật nếu người dùng chọn file mới
            if (imageFile != null && !imageFile.isEmpty()) {
                try {
                    // Đường dẫn lưu file
                    String uploadDir = "src/main/resources/static/images/";
                    Path uploadPath = Paths.get(uploadDir);

                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }

                    String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                    // Lưu đường dẫn web vào DB
                    existingShop.setImage("/images/" + fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // Nếu không có file mới, existingShop.getImage() vẫn giữ nguyên giá trị cũ trong DB

            shopRepository.save(existingShop);
        }

        // Lưu ý: Redirect về đúng trang danh sách shop của Admin
        return "redirect:/admin/shops";
    }
}
