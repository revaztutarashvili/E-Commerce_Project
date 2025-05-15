package com.example.ecommerce.services;

import com.example.ecommerce.models.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User createUser(User user);
}