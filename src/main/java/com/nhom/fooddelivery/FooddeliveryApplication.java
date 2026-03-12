package com.nhom.fooddelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@SpringBootApplication
public class FooddeliveryApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(FooddeliveryApplication.class, args);
    }
}