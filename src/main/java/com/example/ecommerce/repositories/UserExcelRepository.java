package com.example.ecommerce.repositories;

import com.example.ecommerce.models.User;
import com.example.ecommerce.utils.ExcelHelper;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserExcelRepository implements UserRepository {
    private static final String FILE_PATH = "src/main/resources/data/users.xlsx";

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Workbook workbook = ExcelHelper.getWorkbook(FILE_PATH)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                User user = new User();
                user.setUsername(row.getCell(0).getStringCellValue());
                user.setRole(row.getCell(1).getStringCellValue());
                user.setBudget(row.getCell(2).getNumericCellValue());

                users.add(user);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read users from Excel file", e);
        }
        return users;
    }

    @Override
    public User save(User user) {
        try {
            List<User> users = findAll();
            users.removeIf(u -> u.getUsername().equals(user.getUsername()));
            users.add(user);
            ExcelHelper.writeUsers(FILE_PATH, users);
            return user;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save user to Excel file", e);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            return findAll().stream()
                    .filter(u -> u.getUsername().equals(username))
                    .findFirst();
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to find user by username", e);
        }
    }

    @Override
    public void delete(String username) {
        try {
            List<User> users = findAll();
            users.removeIf(u -> u.getUsername().equals(username));
            ExcelHelper.writeUsers(FILE_PATH, users);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete user from Excel file", e);
        }
    }
}