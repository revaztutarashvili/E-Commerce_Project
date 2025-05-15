package com.example.ecommerce.models;

import lombok.Data;

@Data
public class User {
    private String username;
    private String role; // ADMIN or USER
    private double budget = 1000.0;
}