package com.nhom.fooddelivery.controller;

import com.nhom.fooddelivery.entity.Shop;
import com.nhom.fooddelivery.entity.User;
import com.nhom.fooddelivery.repository.ShopRepository;
import com.nhom.fooddelivery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shops")
public class ShopController {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UserRepository userRepository;

    // 1️⃣ Lấy danh sách tất cả shop
    @GetMapping
    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    // 2️⃣ Lấy shop theo ID
    @GetMapping("/{id}")
    public Shop getShopById(@PathVariable Long id) {
        return shopRepository.findById(id).orElse(null);
    }

    // 3️⃣ Tìm shop theo tên
    @GetMapping("/search")
    public List<Shop> searchShopByName(@RequestParam String keyword) {
        return shopRepository.findByNameContainingIgnoreCase(keyword);
    }

    // 4️⃣ Tạo shop mới
    @PostMapping
    public Shop createShop(
            @RequestParam Long ownerId,
            @RequestBody Shop shop
    ) {
        User owner = userRepository.findById(ownerId).orElse(null);
        if (owner == null) {
            return null;
        }

        shop.setOwner(owner);
        return shopRepository.save(shop);
    }

    // 5️⃣ Cập nhật shop
    @PutMapping("/{id}")
    public Shop updateShop(
            @PathVariable Long id,
            @RequestBody Shop newShop
    ) {
        return shopRepository.findById(id).map(shop -> {
            shop.setName(newShop.getName());
            shop.setAddress(newShop.getAddress());
            shop.setImage(newShop.getImage());
            return shopRepository.save(shop);
        }).orElse(null);
    }

    // 6️⃣ Xóa shop
    @DeleteMapping("/{id}")
    public void deleteShop(@PathVariable Long id) {
        shopRepository.deleteById(id);
    }
}
