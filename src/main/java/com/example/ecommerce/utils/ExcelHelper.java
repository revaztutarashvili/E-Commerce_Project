package com.example.ecommerce.utils;

import com.example.ecommerce.models.Order;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.models.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelHelper {

    // Products
    public static void writeProducts(String filePath, List<Product> products) throws IOException {
        writeToExcel(filePath, "Products", products, (row, product) -> {
            row.createCell(0).setCellValue(product.getId());
            row.createCell(1).setCellValue(product.getName());
            row.createCell(2).setCellValue(product.getPrice());
            row.createCell(3).setCellValue(product.getStock());
        });
    }

    // Users
    public static void writeUsers(String filePath, List<User> users) throws IOException {
        writeToExcel(filePath, "Users", users, (row, user) -> {
            row.createCell(0).setCellValue(user.getUsername());
            row.createCell(1).setCellValue(user.getRole());
            row.createCell(2).setCellValue(user.getBudget());
        });
    }

    // Orders
    public static void writeOrders(String filePath, List<Order> orders) throws IOException {
        writeToExcel(filePath, "Orders", orders, (row, order) -> {
            row.createCell(0).setCellValue(order.getId());
            row.createCell(1).setCellValue(order.getUserId());
            row.createCell(2).setCellValue(order.getProductId());
            row.createCell(3).setCellValue(order.getQuantity());
            row.createCell(4).setCellValue(order.getTotalPrice());
        });
    }

    private static <T> void writeToExcel(String filePath, String sheetName,
                                         List<T> items, ExcelWriter<T> writer) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(sheetName);

            // Write data
            for (int i = 0; i < items.size(); i++) {
                Row row = sheet.createRow(i);
                writer.write(row, items.get(i));
            }

            // Save to file
            try (FileOutputStream out = new FileOutputStream(filePath)) {
                workbook.write(out);
            }
        }
    }

    public static Workbook getWorkbook(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("File not found: " + filePath);
        }
        return WorkbookFactory.create(new FileInputStream(file));
    }

    @FunctionalInterface
    private interface ExcelWriter<T> {
        void write(Row row, T item);
    }
}