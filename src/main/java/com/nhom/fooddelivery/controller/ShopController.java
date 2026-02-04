package com.nhom.fooddelivery.controller;

import com.nhom.fooddelivery.entity.*;
import com.nhom.fooddelivery.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/shops")
public class ShopController {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private ShopRepository shopRepository;





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

    // =======================
    // 5️⃣ FORM SỬA FOOD
    // =======================
    @GetMapping("/edit/{id}")
    public String editShop(@PathVariable Long id, Model model) {
        Shop shop = shopRepository.findById(id).orElse(null);
        model.addAttribute("shop", shop);

        return "merchant/shop-form";
    }

    // =======================
    // 6️⃣ XÓA FOOD
    // =======================
    @GetMapping("/delete/{id}")
    public String deleteShop(@PathVariable Long id) {
        shopRepository.deleteById(id);
        return "redirect:/shops";
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