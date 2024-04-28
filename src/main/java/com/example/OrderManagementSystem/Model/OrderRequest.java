package com.example.OrderManagementSystem.Model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

public class OrderRequest {
    private final String userId;
    private final String deliveryAddress;
    private final LocalDateTime orderDate;
    private final Map<String, Integer> items;
    private final double totalAmount;

    public OrderRequest(String userId, String deliveryAddress, LocalDateTime orderDate, Map<String, Integer> items, double totalAmount) {
        this.userId = userId;
        this.deliveryAddress = deliveryAddress;
        this.orderDate = orderDate;
        this.items = items;
        this.totalAmount = totalAmount;
    }

    public String getUserId() {
        return userId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
