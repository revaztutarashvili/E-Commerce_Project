package com.example.ecommerce.repositories;

import com.example.ecommerce.models.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();
    User save(User user);
    Optional<User> findByUsername(String username);
    void delete(String username);
}