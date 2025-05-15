package com.example.ecommerce.services;

import com.example.ecommerce.models.Order;
import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Order createOrder(Order order);
}