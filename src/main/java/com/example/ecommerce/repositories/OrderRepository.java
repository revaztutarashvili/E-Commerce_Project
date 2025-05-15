package com.example.ecommerce.repositories;

import com.example.ecommerce.models.Order;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    List<Order> findAll();
    Order save(Order order);
    Optional<Order> findById(String id);
    void delete(String id);
}