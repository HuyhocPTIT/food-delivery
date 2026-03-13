package com.nhom.fooddelivery.controller;

import com.nhom.fooddelivery.entity.Food;
import com.nhom.fooddelivery.repository.FoodRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private FoodRepository foodRepository;

    @GetMapping("/")
    public String index(Model model, HttpSession session,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "id,desc") String sort,
                        @RequestParam(required = false) Double minPrice,
                        @RequestParam(required = false) Double maxPrice,
                        @RequestParam(required = false) Long categoryId) {

        int pageSize = 9;

        // 1. Xử lý sắp xếp
        String[] sortParts = sort.split(",");
        String sortBy = sortParts[0];
        Sort.Direction direction = sortParts[1].equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(direction, sortBy));

        // 2. Lọc giá
        Page<Food> foodPage;

        if (categoryId != null) {
            // Lọc theo danh mục
            foodPage = foodRepository.findByCategoryId(categoryId, pageable);
        } else if (minPrice != null && maxPrice != null) {
            foodPage = foodRepository.findByPriceBetween(minPrice, maxPrice, pageable);
        } else if (minPrice != null) {
            foodPage = foodRepository.findByPriceGreaterThanEqual(minPrice, pageable);
        } else if (maxPrice != null) {
            foodPage = foodRepository.findByPriceLessThanEqual(maxPrice, pageable);
        } else {
            foodPage = foodRepository.findAll(pageable);
        }

        // 3. Đẩy dữ liệu sang JSP
        model.addAttribute("foods", foodPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", foodPage.getTotalPages());
        model.addAttribute("currentSort", sort);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("categoryId", categoryId);


        // 4. Giỏ hàng
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        int cartCount = 0;
        if (cart != null) {
            for (int qty : cart.values()) cartCount += qty;
        }
        model.addAttribute("cartCount", cartCount);

        return "index";
    }
}