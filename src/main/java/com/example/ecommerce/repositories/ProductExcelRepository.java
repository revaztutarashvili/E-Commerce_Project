package com.example.ecommerce.repositories;

import com.example.ecommerce.models.Product;
import com.example.ecommerce.utils.ExcelHelper;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProductExcelRepository implements ProductRepository {
    private static final String FILE_PATH = "src/main/resources/data/products.xlsx";

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try {
            Workbook workbook = ExcelHelper.getWorkbook(FILE_PATH);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                Product product = new Product();
                product.setId(row.getCell(0).getStringCellValue());
                product.setName(row.getCell(1).getStringCellValue());
                product.setPrice(row.getCell(2).getNumericCellValue());
                product.setStock((int) row.getCell(3).getNumericCellValue());

                products.add(product);
            }
            workbook.close();
        } catch (IOException e) {
            // Log the error and return empty list or throw runtime exception
            System.err.println("Error reading Excel file: " + e.getMessage());
            // Alternatively: throw new RuntimeException("Failed to read products", e);
        }
        return products;
    }

    @Override
    public Product save(Product product) {
        try {
            if (product.getId() == null) {
                product.setId(UUID.randomUUID().toString());
            }

            List<Product> products = findAll();
            products.removeIf(p -> p.getId().equals(product.getId()));
            products.add(product);

            ExcelHelper.writeProducts(FILE_PATH, products);
            return product;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save product", e);
        }
    }

    @Override
    public Optional<Product> findById(String id) {
        try {
            return findAll().stream()
                    .filter(p -> p.getId().equals(id))
                    .findFirst();
        } catch (Exception e) {
            throw new RuntimeException("Failed to find product by id", e);
        }
    }

    @Override
    public void delete(String id) {
        try {
            List<Product> products = findAll();
            products.removeIf(p -> p.getId().equals(id));
            ExcelHelper.writeProducts(FILE_PATH, products);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete product", e);
        }
    }
}