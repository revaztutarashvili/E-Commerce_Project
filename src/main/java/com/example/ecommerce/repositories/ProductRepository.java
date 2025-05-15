package com.example.ecommerce.repositories;

import com.example.ecommerce.models.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();
    Product save(Product product);
    Optional<Product> findById(String id);
    void delete(String id);
}