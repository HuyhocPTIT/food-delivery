package com.nhom.fooddelivery.controller;

import com.nhom.fooddelivery.entity.Order;
import com.nhom.fooddelivery.entity.User;
import com.nhom.fooddelivery.repository.OrderRepository;
import jakarta.servlet.http.HttpSession;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/shipper")
public class ShipperController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        List<Order> orders = orderRepository.findByStatus("READY");

        model.addAttribute("orders", orders);
        model.addAttribute("shipperId", currentUser != null ? currentUser.getId() : null);
        return "shipper/order-waiting";
    }

    @GetMapping("/delivering")
    public String delivering(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            model.addAttribute("orders", Collections.emptyList());
            return "shipper/order-delivering";
        }

        List<Order> orders =
                orderRepository.findByShipperIdAndStatus(currentUser.getId(), "SHIPPING");
        model.addAttribute("orders", orders);
        model.addAttribute("shipperId", currentUser.getId());
        return "shipper/order-delivering";
    }

    @GetMapping("/stats")
    public String stats(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");

        Long deliveredCount = 0L;
        Double totalEarnings = 0.0;
        Double avgRating = 0.0;
        List<Order> completedOrders = new ArrayList<>();

        if (currentUser != null) {
            deliveredCount =
                    orderRepository.countByShipperIdAndStatus(currentUser.getId(), "DELIVERED");

            totalEarnings =
                    orderRepository.sumEarningsByShipper(currentUser.getId());

            avgRating =
                    orderRepository.getAverageRatingByShipper(currentUser.getId());

            completedOrders =
                    orderRepository.findByShipperIdAndStatus(currentUser.getId(), "DELIVERED");
        }

        // Format giá tiền VN
        NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
        String totalEarningsStr = nf.format(totalEarnings);

        model.addAttribute("deliveredCount", deliveredCount);
//        model.addAttribute("totalEarnings", df.format(totalEarnings));
        model.addAttribute("totalEarnings", totalEarningsStr);
        model.addAttribute("avgRating", avgRating);
        model.addAttribute("completedOrders", completedOrders);

        return "shipper/order-stats";
    }
}
