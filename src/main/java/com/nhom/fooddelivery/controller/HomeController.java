package com.nhom.fooddelivery.controller;

import com.nhom.fooddelivery.entity.Food;
import com.nhom.fooddelivery.repository.FoodRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private FoodRepository foodRepository;

    @GetMapping("/")
    public String index(Model model, @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "id,desc") String sort,
                                    @RequestParam(required = false) Double minPrice,
                                    @RequestParam(required = false) Double maxPrice) {
        int pageSize = 9;

        // Tách chuỗi "price,asc" thành tên cột "price" và hướng "asc"
        String[] sortParts = sort.split(",");
        String sortBy = sortParts[0];
        Sort.Direction direction = sortParts[1].equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(direction, sortBy));
        Page<Food> foodPage;
        //nhập cả min với max thì mới lọc
        if (minPrice != null && maxPrice != null) {
            foodPage = foodRepository.findByPriceBetween(minPrice, maxPrice, pageable);
        } else {
            foodPage = foodRepository.findAll(pageable);
        }
        // 2. Đẩy list 9 món vào model (Dùng foodPage.getContent())
        model.addAttribute("foods", foodPage.getContent());

        // 3. Đẩy danh sách này sang JSP với tên biến là "foods"
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", foodPage.getTotalPages());

        // Gửi tham số sort hiện tại sang JSP để các nút phân trang không bị mất lọc
        model.addAttribute("currentSort", sort);
    public String home(Model model, HttpSession session) {

        // ===== LAY DANH SACH MON AN =====
        List<Food> foodList = foodRepository.findAll();
        model.addAttribute("foods", foodList);

        // ===== TINH SO LUONG TRONG GIO HANG =====
        Map<Long, Integer> cart =
                (Map<Long, Integer>) session.getAttribute("cart");

        int cartCount = 0;
        if (cart != null) {
            for (int qty : cart.values()) {
                cartCount += qty;
            }
        }

        model.addAttribute("cartCount", cartCount);

        // Message test (co the xoa sau)
        model.addAttribute("message", "Connect Spring Boot and JSP was successful!");

        // ===== TRA VE TRANG CHU =====
        return "index"; // hoac "index" tuy JSP cua ban
    }
}
