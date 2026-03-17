package com.nhom.fooddelivery.controller;

import com.nhom.fooddelivery.constant.UserRole;
import com.nhom.fooddelivery.entity.*;
import com.nhom.fooddelivery.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/shops")
public class ShopController {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    private static final String UPLOAD_DIR = "src/main/resources/static/images/";

    // ==========================================
    // PHẦN 1: PUBLIC (KHÁCH HÀNG XEM)
    // ==========================================

    // =======================
    // 1️⃣ HIỂN THỊ DANH SÁCH SHOP
    // =======================
    @GetMapping
    public String listShops(Model model) {
        List<Shop> shops= shopRepository.findByStatus("ACTIVE");

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
            return "redirect:/shops/edit";
        }

        model.addAttribute("shop", new Shop());

        return  "merchant/shop-register";
    }

    @PostMapping("/register")
    public String processRegisterShop(
            @ModelAttribute Shop shop,
            @RequestParam("imageFile") MultipartFile imageFile, // Nhận file từ form
            HttpSession session,
            RedirectAttributes ra) {

        User currentUser = (User) session.getAttribute("currentUser");
        if(currentUser == null) return "redirect:/login";

        if (!imageFile.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                String uploadDir = "src/main/resources/static/images/";
                java.nio.file.Path path = java.nio.file.Paths.get(uploadDir + fileName);
                java.nio.file.Files.copy(imageFile.getInputStream(), path, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                shop.setImage("/images/" + fileName); // Lưu đường dẫn vào DB
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        shop.setOwner(currentUser);
        shop.setStatus("PENDING");
        shopRepository.save(shop);

        currentUser.setStatus("PENDING_MERCHANT");
        userRepository.save(currentUser);

        ra.addFlashAttribute("message", "pending");

        return "redirect:/shops";
    }


    // =======================
    // SHOP MANAGEMENT
    // =======================

    // View and Edit Shop Info
    @GetMapping("/edit")
    public String editShop(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || currentUser.getRole() != UserRole.MERCHANT) {
            return "redirect:/login";
        }
        Shop shop = shopRepository.findByOwnerId(currentUser.getId());
        if (shop == null) {
            return "redirect:/shops/register";
        }
        model.addAttribute("shop", shop);
        return "merchant/shop-form";
    }

    // Update Shop Info
    @PostMapping("/update")
    public String updateShop(@ModelAttribute Shop shopData,
                             @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                             HttpSession session,
                             RedirectAttributes ra) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || currentUser.getRole() != UserRole.MERCHANT) {
            return "redirect:/login";
        }
        Shop currentShop = shopRepository.findByOwnerId(currentUser.getId());
        if (currentShop == null) {
            return "redirect:/shops/register";
        }

        currentShop.setName(shopData.getName());
        currentShop.setAddress(shopData.getAddress());

        // Handle image upload
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                currentShop.setImage("/images/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                ra.addFlashAttribute("error", "Failed to upload image");
                return "redirect:/shops/edit";
            }
        }
        // If no new image, keep the old one

        shopRepository.save(currentShop);
        ra.addFlashAttribute("success", "Shop updated successfully");
        return "redirect:/shops/edit";
    }

    // =======================
    // FOOD MANAGEMENT
    // =======================

    // List Foods for Shop
    @GetMapping("/foods")
    public String listFoods(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || currentUser.getRole() != UserRole.MERCHANT) {
            return "redirect:/login";
        }
        Shop shop = shopRepository.findByOwnerId(currentUser.getId());
        if (shop == null) {
            return "redirect:/shops/register";
        }
        List<Food> foods = foodRepository.findByShop(shop);
        model.addAttribute("foods", foods);
        return "merchant/food-list";
    }

    // Show Create Food Form
    @GetMapping("/foods/create")
    public String createFood(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || currentUser.getRole() != UserRole.MERCHANT) {
            return "redirect:/login";
        }
        model.addAttribute("food", new Food());
        model.addAttribute("categories", categoryRepository.findAll());
        return "merchant/food-form";
    }

    // Save New Food
    @PostMapping("/foods/save")
    public String saveFood(@ModelAttribute Food food,
                           @RequestParam Long categoryId,
                           @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                           HttpSession session,
                           RedirectAttributes ra) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || currentUser.getRole() != UserRole.MERCHANT) {
            return "redirect:/login";
        }
        Shop shop = shopRepository.findByOwnerId(currentUser.getId());
        if (shop == null) {
            return "redirect:/shops/register";
        }
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            ra.addFlashAttribute("error", "Invalid category");
            return "redirect:/shops/foods/create";
        }

        food.setShop(shop);
        food.setCategory(category);

        // Handle image upload
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                food.setImage("/images/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                ra.addFlashAttribute("error", "Failed to upload image");
                return "redirect:/shops/foods/create";
            }
        }

        foodRepository.save(food);
        ra.addFlashAttribute("success", "Food created successfully");
        return "redirect:/shops/foods";
    }

    // Show Edit Food Form
    @GetMapping("/foods/edit/{id}")
    public String editFood(@PathVariable Long id, HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || currentUser.getRole() != UserRole.MERCHANT) {
            return "redirect:/login";
        }
        Food food = foodRepository.findById(id).orElse(null);
        if (food == null || !food.getShop().getOwner().getId().equals(currentUser.getId())) {
            return "redirect:/shops/foods";
        }
        model.addAttribute("food", food);
        model.addAttribute("categories", categoryRepository.findAll());
        return "merchant/food-form";
    }

    // Update Food
    @PostMapping("/foods/update/{id}")
    public String updateFood(@PathVariable Long id,
                             @ModelAttribute Food foodData,
                             @RequestParam Long categoryId,
                             @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                             HttpSession session,
                             RedirectAttributes ra) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || currentUser.getRole() != UserRole.MERCHANT) {
            return "redirect:/login";
        }
        Food food = foodRepository.findById(id).orElse(null);
        if (food == null || !food.getShop().getOwner().getId().equals(currentUser.getId())) {
            return "redirect:/shops/foods";
        }

        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            ra.addFlashAttribute("error", "Invalid category");
            return "redirect:/shops/foods/edit/" + id;
        }

        food.setName(foodData.getName());
        food.setPrice(foodData.getPrice());
        food.setDescription(foodData.getDescription());
        food.setCategory(category);

        // Handle image upload
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                food.setImage("/images/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                ra.addFlashAttribute("error", "Failed to upload image");
                return "redirect:/shops/foods/edit/" + id;
            }
        }
        // If no new image, keep the old one

        foodRepository.save(food);
        ra.addFlashAttribute("success", "Food updated successfully");
        return "redirect:/shops/foods";
    }

    // Delete Food
    @GetMapping("/foods/delete/{id}")
    public String deleteFood(@PathVariable Long id, HttpSession session, RedirectAttributes ra) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || currentUser.getRole() != UserRole.MERCHANT) {
            return "redirect:/login";
        }
        Food food = foodRepository.findById(id).orElse(null);
        if (food == null || !food.getShop().getOwner().getId().equals(currentUser.getId())) {
            return "redirect:/shops/foods";
        }
        foodRepository.delete(food);
        ra.addFlashAttribute("success", "Food deleted successfully");
        return "redirect:/shops/foods";
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