package com.example.ecommerce.models;

import lombok.Data;

@Data
public class Product {
    private String id;
    private String name;
    private double price;
    private int stock;
}