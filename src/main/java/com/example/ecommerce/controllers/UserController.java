package com.example.ecommerce.controllers;

import com.example.ecommerce.models.User;
import com.example.ecommerce.services.UserService;
import com.example.ecommerce.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
//    private final UserService userService;
    private final UserServiceImpl userServiceImpl;

    @GetMapping("/get-all-users")
    public List<User> getAllUsers() {
        return userServiceImpl.getAllUsers();
    }

    @PostMapping("/create-user")
    public User createUser(@RequestBody User user) {
        return userServiceImpl.createUser(user);
    }
}