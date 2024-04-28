package com.example.OrderManagementSystem.Model;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class Order {
    private String id;
    private String userId;
    private String deliveryAddress;
    private LocalDateTime orderDate;
    private Map<String, Integer> items;
    private OrderStatus status;
    private double totalAmount;

    public Order(String id, String userId, String deliveryAddress, LocalDateTime orderDate, Map<String, Integer> items, double totalAmount) {
        this.id = id;
        this.userId = userId;
        this.deliveryAddress = deliveryAddress;
        this.orderDate = orderDate;
        this.items = items;
        this.status = OrderStatus.PENDING;
        this.totalAmount = totalAmount;
    }

    public String getId() {
        return id;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public void setItems(Map<String, Integer> items) {
        this.items = items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }


    public double getTotalAmount() {
        return totalAmount;
    }
}
