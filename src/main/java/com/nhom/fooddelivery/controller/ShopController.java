package com.nhom.fooddelivery.controller;

import com.nhom.fooddelivery.constant.UserRole;
import com.nhom.fooddelivery.entity.*;
import com.nhom.fooddelivery.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/shops")
public class ShopController {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private ShopRepository shopRepository;

    // ==========================================
    // PHẦN 1: PUBLIC (KHÁCH HÀNG XEM)
    // ==========================================

    // =======================
    // 1️⃣ HIỂN THỊ DANH SÁCH SHOP
    // =======================
    @GetMapping
    public String listShops(Model model) {
        List<Shop> shops= shopRepository.findAll();

        model.addAttribute("shops", shops);
        return "merchant/shop-list";
    }

    // =======================
    // 2️⃣ XEM CHI TIẾT FOOD
    // =======================
    @GetMapping("/{id}")
    public String shopDetail(@PathVariable Long id, Model model) {
        Shop shop = shopRepository.findById(id).orElse(null);

        if (shop == null) {
            return "redirect:/shops";
        }


        model.addAttribute("shop", shop);
        return "merchant/shop-detail";
    }
    @GetMapping("/my-shop")
    public String myShop(HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || currentUser.getRole() != UserRole.MERCHANT) {
            return "redirect:/login";
        }
        Shop shop = shopRepository.findByOwnerId(currentUser.getId());
        if (shop == null) return "redirect:/shops/register";

        return "redirect:/shops/" + shop.getId();
    }

//    // =======================
//    // 3️⃣ FORM THÊM FOOD
//    // =======================
//    @GetMapping("/create")
//    public String showCreateForm(Model model) {
//        model.addAttribute("food", new Food());
//        model.addAttribute("shops", shopRepository.findAll());
//        model.addAttribute("categories", categoryRepository.findAll());
//        return "merchant/food-form";
//    }

    // =======================
    // 4️⃣ LƯU FOOD
    // =======================
//    @PostMapping("/save")
//    public String saveFood(
//            @ModelAttribute Shop shop,
//            @RequestParam Long shopId,
//            @RequestParam Long categoryId
//    ) {
//        Shop shop = shopRepository.findById(shopId).orElse(null);
//        Category category = categoryRepository.findById(categoryId).orElse(null);
//
//        food.setShop(shop);
//        food.setCategory(category);
//
//        foodRepository.save(food);
//        return "redirect:/foods";
//    }

    // ==========================================
    // PHẦN 2: MERCHANT (QUẢN LÝ QUÁN CỦA MÌNH)
    // ==========================================

    // 3️⃣ Form đăng ký mở quán (Dành cho User muốn lên Merchant)
    @GetMapping("/register")
    public String showRegisterForm(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser == null) {
            return "redirect:/login";
        }
        // Nếu đã là Merchant rồi thì không cho đăng ký mới (Vì OneToOne)
        if(currentUser.getRole() == UserRole.MERCHANT){
            return "redirect:/shops/my-shop/edit";
        }

        model.addAttribute("shop", new Shop());

        return  "merchant/shop-register";
    }

    @PostMapping("/register")
    public String processRegisterShop(@ModelAttribute Shop shop, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if(currentUser == null ) return "redirect:/login";

        // Gán chủ sở hữu là người đang đăng nhập
        shop.setOwner(currentUser);

        // set status
        shop.setStatus("PENDING");

        shopRepository.save(shop);

        return "redirect:/profile?message=cho_duyet";
    }


    // =======================
    // Form sửa quán
    // =======================
    @GetMapping("/my-shop/edit")
    public String editMyShop(HttpSession session, Model model) {

        User currentUser = (User) session.getAttribute("currentUser");

        // merchant mới được vào
        if (currentUser == null || currentUser.getRole() != UserRole.MERCHANT) {
            return "redirect:/login";
        }
        Shop shop = shopRepository.findByOwnerId(currentUser.getId());

        // lỗi data, merchant nhưng chưa có shop
        if(shop == null) return "redirect:/shops/register";

        model.addAttribute("shop", shop);
        return "merchant/shop-form";
    }

    // =======================
    // update
    // =======================
    @PostMapping("/my-shop/update")
    public String updateMyShop(@ModelAttribute Shop shopData, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");

        if(currentUser == null || currentUser.getRole() != UserRole.MERCHANT) return "redirect:/login";

        Shop currentShop = shopRepository.findByOwnerId(currentUser.getId());

        if(currentShop != null){
            currentShop.setName(shopData.getName());
            currentShop.setAddress(shopData.getAddress());
            currentShop.setImage(shopData.getImage());

            shopRepository.save(currentShop);
        }

        return "redirect:/shops/my-shop/edit?success=true";
    }


    // =======================
    // 7️⃣ JSON (test)
    // =======================
    @GetMapping("/api")
    @ResponseBody
    public List<Shop> getShopsJson() {
        return shopRepository.findAll();
    }
}