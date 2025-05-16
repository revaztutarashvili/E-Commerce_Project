package com.example.ecommerce.utils;

import com.example.ecommerce.models.Order;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.models.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;

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

            // Create headers
            createHeaders(sheet, sheetName);

            // Write data (starting from row 1 to skip headers)
            for (int i = 0; i < items.size(); i++) {
                Row row = sheet.createRow(i + 1);
                writer.write(row, items.get(i));
            }

            // Auto-size columns
            autoSizeColumns(sheet);

            // Save to file
            try (FileOutputStream out = new FileOutputStream(filePath)) {
                workbook.write(out);
            }
        }
    }

    private static void createHeaders(Sheet sheet, String sheetName) {
        Row headerRow = sheet.createRow(0);

        switch (sheetName) {
            case "Products":
                headerRow.createCell(0).setCellValue("ID");
                headerRow.createCell(1).setCellValue("Name");
                headerRow.createCell(2).setCellValue("Price");
                headerRow.createCell(3).setCellValue("Stock");
                break;
            case "Users":
                headerRow.createCell(0).setCellValue("Username");
                headerRow.createCell(1).setCellValue("Role");
                headerRow.createCell(2).setCellValue("Budget");
                break;
            case "Orders":
                headerRow.createCell(0).setCellValue("ID");
                headerRow.createCell(1).setCellValue("User ID");
                headerRow.createCell(2).setCellValue("Product ID");
                headerRow.createCell(3).setCellValue("Quantity");
                headerRow.createCell(4).setCellValue("Total Price");
                break;
        }
    }

    private static void autoSizeColumns(Sheet sheet) {
        for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
            sheet.autoSizeColumn(i);
        }
    }

    public static Workbook getWorkbook(String filePath) throws IOException {
        File file = new File(filePath);

        // If file doesn't exist, create a new one with headers
        if (!file.exists()) {
            Workbook workbook = new XSSFWorkbook();

            // Create empty sheets with headers
            createHeaders(workbook.createSheet("Products"), "Products");
            createHeaders(workbook.createSheet("Users"), "Users");
            createHeaders(workbook.createSheet("Orders"), "Orders");

            // Save the new file
            try (FileOutputStream out = new FileOutputStream(filePath)) {
                workbook.write(out);
            }
            return workbook;
        }

        // If file exists, return it
        try (FileInputStream fis = new FileInputStream(file)) {
            return WorkbookFactory.create(fis);
        }
    }

    @FunctionalInterface
    private interface ExcelWriter<T> {
        void write(Row row, T item);
    }
}