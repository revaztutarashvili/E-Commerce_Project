package com.example.ecommerce.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Order {
    private String id;
    private String userId;
    private String productId;
    private int quantity;
    private double totalPrice;
    private LocalDateTime orderDate = LocalDateTime.now();
}