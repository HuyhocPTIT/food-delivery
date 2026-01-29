package com.nhom.fooddelivery;

import com.nhom.fooddelivery.constant.UserRole;
import com.nhom.fooddelivery.entity.*;
import com.nhom.fooddelivery.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component
public class DataSeeder implements CommandLineRunner {
    @Autowired private UserRepository userRepo;
    @Autowired private ShopRepository shopRepo;
    @Autowired private CategoryRepository categoryRepo;
    @Autowired private FoodRepository foodRepo;

    @Override
    public void run(String... args) throws Exception {
        if (userRepo.count() > 0) return;

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("123"); // Lưu ý: Nên dùng BCrypt sau này
        admin.setFullName("Nguyễn Văn A");
        admin.setRole(UserRole.ADMIN);
        userRepo.save(admin);

        System.out.println(">>> ĐÃ NẠP DỮ LIỆU MẪU THÀNH CÔNG!");
    }
}
