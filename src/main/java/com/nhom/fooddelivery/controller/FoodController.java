package com.nhom.fooddelivery.controller;

import com.nhom.fooddelivery.entity.*;
import com.nhom.fooddelivery.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/foods")
public class FoodController {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // =======================
    // 1️⃣ HIỂN THỊ DANH SÁCH FOOD (JSP)
    // =======================
    @GetMapping
    public String listFoods(Model model) {
        model.addAttribute("foods", foodRepository.findAll());
        return "merchant/food-list"; // food-list.jsp
    }

    // =======================
    // 2️⃣ FORM THÊM FOOD
    // =======================
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("food", new Food());
        model.addAttribute("shops", shopRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "merchant/food-form"; // food-form.jsp
    }

    // =======================
    // 3️⃣ LƯU FOOD
    // =======================
    @PostMapping("/save")
    public String saveFood(
            @ModelAttribute Food food,
            @RequestParam Long shopId,
            @RequestParam Long categoryId
    ) {
        Shop shop = shopRepository.findById(shopId).orElse(null);
        Category category = categoryRepository.findById(categoryId).orElse(null);

        food.setShop(shop);
        food.setCategory(category);

        foodRepository.save(food);
        return "redirect:/foods";
    }

    // =======================
    // 4️⃣ FORM SỬA FOOD
    // =======================
    @GetMapping("/edit/{id}")
    public String editFood(@PathVariable Long id, Model model) {
        Food food = foodRepository.findById(id).orElse(null);
        model.addAttribute("food", food);
        model.addAttribute("shops", shopRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "merchant/food-form";
    }

    // =======================
    // 5️⃣ XÓA FOOD
    // =======================
    @GetMapping("/delete/{id}")
    public String deleteFood(@PathVariable Long id) {
        foodRepository.deleteById(id);
        return "redirect:/foods";
    }

    // =======================
    // 6️⃣ (OPTIONAL) JSON – nếu cần test
    // =======================
    @GetMapping("/api")
    @ResponseBody
    public List<Food> getFoodsJson() {
        return foodRepository.findAll();
    }
}
