package com.example.ecommerce.config;

import com.example.ecommerce.utils.ExcelHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Configuration
public class AppConfig {

    @PostConstruct
    public void init() {
        try {
            // Initialize Excel files if they don't exist
            ExcelHelper.writeProducts("src/main/resources/data/products.xlsx", List.of());
            ExcelHelper.writeUsers("src/main/resources/data/users.xlsx", List.of());
            ExcelHelper.writeOrders("src/main/resources/data/orders.xlsx", List.of());
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize Excel files", e);
        }
    }
}