package com.example.ecommerce.repositories;

import com.example.ecommerce.models.Order;
import com.example.ecommerce.utils.ExcelHelper;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class OrderExcelRepository implements OrderRepository {
    private static final String FILE_PATH = "src/main/resources/data/orders.xlsx";

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        try (Workbook workbook = ExcelHelper.getWorkbook(FILE_PATH)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                Order order = new Order();
                order.setId(row.getCell(0).getStringCellValue());
                order.setUserId(row.getCell(1).getStringCellValue());
                order.setProductId(row.getCell(2).getStringCellValue());
                order.setQuantity((int) row.getCell(3).getNumericCellValue());
                order.setTotalPrice(row.getCell(4).getNumericCellValue());

                orders.add(order);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read orders from Excel file", e);
        }
        return orders;
    }

    @Override
    public Order save(Order order) {
        try {
            if (order.getId() == null) {
                order.setId(UUID.randomUUID().toString());
            }

            List<Order> orders = findAll();
            orders.removeIf(o -> o.getId().equals(order.getId()));
            orders.add(order);

            ExcelHelper.writeOrders(FILE_PATH, orders);
            return order;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save order to Excel file", e);
        }
    }

    @Override
    public Optional<Order> findById(String id) {
        try {
            return findAll().stream()
                    .filter(o -> o.getId().equals(id))
                    .findFirst();
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to find order by ID", e);
        }
    }

    @Override
    public void delete(String id) {
        try {
            List<Order> orders = findAll();
            orders.removeIf(o -> o.getId().equals(id));
            ExcelHelper.writeOrders(FILE_PATH, orders);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete order from Excel file", e);
        }
    }
}