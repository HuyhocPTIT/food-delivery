package com.nhom.fooddelivery.controller;

import com.nhom.fooddelivery.entity.*;
import com.nhom.fooddelivery.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    // 1. Danh sách đơn đang chờ shipper nhận
    @GetMapping("/waiting")
    public String getWaitingOrders(Model model){
        List<Order> orders = orderRepository.findByStatus("READY");
        model.addAttribute("orders", orders);
        return "shipper/order-waiting";
    }

    // 2. Shipper nhận đơn
    @PostMapping("/accept")
    public String acceptOrder(
            @RequestParam Long orderId,
            @RequestParam Long shipperId
    ){
        Order order = orderRepository.findById(orderId).orElse(null);
        User shipper = userRepository.findById(shipperId).orElse(null);

        if (order != null && shipper != null){
            order.setShipper(shipper);
            order.setStatus("SHIPPING");
            orderRepository.save(order);
        }

        return "redirect:/orders/delivering?shipperId=" + shipperId;
    }

    // 3. Đơn đang giao của Shipper
    @GetMapping("/delivering")
    public String getDeliveringOrders(
            @RequestParam Long shipperId,
            Model model
    ){
        List<Order> orders =
                orderRepository.findByShipperIdAndStatus(shipperId, "SHIPPING");
        model.addAttribute("orders", orders);
        model.addAttribute("shipperId", shipperId);
        return "shipper/order-delivering";
    }

    // 4. Shipper hoàn thành giao hàng
    @PostMapping("/complete")
    public String completeOrder(
            @RequestParam Long orderId,
            @RequestParam Long shipperId
    ){
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order != null){
            order.setStatus("DELIVERED");
            order.setDeliveredAt(LocalDateTime.now());
            orderRepository.save(order);
        }

        return "redirect:/orders/delivering?shipperId=" + shipperId;
    }

    // 5. Thống kê đơn đã giao
    @GetMapping("/stats")
    public String shipperStats(
            @RequestParam Long shipperId,
            Model model
    ){
        // số đơn đã giao
        Long deliveredCount =
                orderRepository.countByShipperIdAndStatus(shipperId, "DELIVERED");

        // tổng tiền đã giao
        Double totalEarnings =
                Optional.ofNullable(orderRepository.sumEarningsByShipper(shipperId)).orElse(0.0);

        // điểm đánh giá shipper trung bình
        Double avgRating =
                Optional.ofNullable(orderRepository.getAverageRatingByShipper(shipperId)).orElse(0.0);

        model.addAttribute("totalEarnings", totalEarnings);
        model.addAttribute("avgRating", avgRating);
        model.addAttribute("deliveredCount", deliveredCount);
        return "shipper/order-stats";
    }
}
