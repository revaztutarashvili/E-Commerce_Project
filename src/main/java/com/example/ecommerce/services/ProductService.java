package com.example.ecommerce.services;

import com.example.ecommerce.models.Product;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product createProduct(Product product);
    void deleteProduct(String id);
}