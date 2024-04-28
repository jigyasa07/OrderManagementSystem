package com.example.OrderManagementSystem.Service;

import com.example.OrderManagementSystem.Model.Order;
import com.example.OrderManagementSystem.Model.OrderRequest;
import com.example.OrderManagementSystem.Model.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Service
public class OrderService {
    private final Map<String, Order> orderMap;

    public OrderService(Map<String, Order> orderMap) {
        this.orderMap = new ConcurrentHashMap<>();
    }

    public Order createOrder(OrderRequest orderRequest) {
        // Check if an order with the same items already exists
        for (Order existingOrder : orderMap.values()) {
            if (existingOrder.getItems().equals(orderRequest.getItems()) && existingOrder.getUserId().equals(orderRequest.getUserId())) {
                return existingOrder;
            }
        }

        // Generate order ID
        String orderId = UUID.randomUUID().toString();

        // Create order
        Order order = new Order(orderId, orderRequest.getUserId(), orderRequest.getDeliveryAddress(), orderRequest.getOrderDate(), orderRequest.getItems(), orderRequest.getTotalAmount());
        orderMap.put(orderId, order);

        return order;
    }

    public Order getOrderDetails(String orderId) {
        return orderMap.get(orderId);
    }

    public Order updateOrderStatus(String orderId, String status) {
        Order order = orderMap.get(orderId);

        if (order != null) {
            Optional<OrderStatus> optionalOrderStatus = Stream.of(OrderStatus.values())
                    .filter(e -> e.toString().equals(status))
                    .findFirst();
            OrderStatus newOrderStatus =  optionalOrderStatus.orElse(order.getStatus());
            // Check if the status is already updated
            if (newOrderStatus == order.getStatus()) {
                return order;
            }

            order.setStatus(newOrderStatus);
        }
        return order;
    }

}
