package com.nhom.fooddelivery.controller;

import com.nhom.fooddelivery.entity.Order;
import com.nhom.fooddelivery.entity.User;
import com.nhom.fooddelivery.repository.OrderRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.nhom.fooddelivery.constant.UserRole.SHIPPER;

@Controller
@RequestMapping("/shipper")
public class ShipperController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || currentUser.getRole() != SHIPPER){
            return "redirect:/login";
        }

        Long readyCount = orderRepository.countByStatus("READY");
        List<Order> deliveringOrders = orderRepository.findByShipperIdAndStatus(currentUser.getId(), "SHIPPING");
        Long deliveringCount = (long) deliveringOrders.size();
        Long deliveredCount =orderRepository.countByShipperIdAndStatus(currentUser.getId(), "DELIVERED");
        Double avgRating = orderRepository.getAverageRatingByShipper(currentUser.getId());
        avgRating = avgRating != null ? avgRating : 0.0;

        model.addAttribute("readyCount", readyCount);
        model.addAttribute("deliveringCount", deliveringCount);
        model.addAttribute("deliveredCount", deliveredCount);
        model.addAttribute("avgRating", avgRating);
        model.addAttribute("deliveringOrders", deliveringOrders);
        return "shipper/shipper-dashboard";
    }

    @GetMapping("/waiting")
    public String watting(HttpSession session, Model model){
        User currenUser = (User) session.getAttribute("currentUser");
        if (currenUser == null || currenUser.getRole() != SHIPPER){
            return "redirect:/login";
        }

        List<Order> orders = orderRepository.findByStatus("READY");
        model.addAttribute("orders", orders);
        model.addAttribute("shipperId", session.getId());
        return "shipper/order-waiting";
    }

    @GetMapping("/delivering")
    public String delivering(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || currentUser.getRole() != SHIPPER){
            return "redirect:/login";
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
        if (currentUser == null || currentUser.getRole() != SHIPPER){
            return "redirect:/login";
        }

        Long deliveredCount = 0L;
        Double totalEarnings = 0.0;
        Double avgRating = 0.0;
        List<Order> completedOrders = new ArrayList<>();

        deliveredCount =
                orderRepository.countByShipperIdAndStatus(currentUser.getId(), "DELIVERED");

        totalEarnings =
                orderRepository.sumEarningsByShipper(currentUser.getId());

        avgRating =
                orderRepository.getAverageRatingByShipper(currentUser.getId());

        completedOrders =
                orderRepository.findByShipperIdAndStatus(currentUser.getId(), "DELIVERED");

        totalEarnings = totalEarnings != null ? totalEarnings : 0.0;
        avgRating = avgRating != null ? avgRating : 0.0;

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