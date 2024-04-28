package com.example.OrderManagementSystem.Controller;

import com.example.OrderManagementSystem.Model.Order;
import com.example.OrderManagementSystem.Model.OrderRequest;
import com.example.OrderManagementSystem.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/orders")
@EnableAsync
public class OrderController {
    private final Map<String, Lock> orderLocks = new ConcurrentHashMap<>();
    private final OrderService orderService;

    private final String CANCEL_ORDER = "CANCELLED";

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<?>> createOrder(@RequestBody OrderRequest orderRequest) {
        return CompletableFuture.supplyAsync(() -> {
            if (orderRequest.getUserId() == null || orderRequest.getDeliveryAddress() == null ||
                    orderRequest.getOrderDate() == null || orderRequest.getItems() == null ||
                    orderRequest.getTotalAmount() == 0.0) {
                return ResponseEntity.badRequest().build();
            }
            Order order = orderService.createOrder(orderRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        });
    }

    @GetMapping("/{orderId}")
    public CompletableFuture<ResponseEntity<?>> getOrderDetails(@PathVariable String orderId) {
        return CompletableFuture.supplyAsync(() -> {
            if (orderId.isEmpty() || orderId == null){
                return ResponseEntity.notFound().build(); //add checks for valid orderId
            }
            Order order = orderService.getOrderDetails(orderId);
            return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
        });
    }

    @PutMapping("/{orderId}")
    public CompletableFuture<ResponseEntity<?>> updateOrderStatus(@PathVariable String orderId, @RequestParam String status) {
        return CompletableFuture.supplyAsync(() -> {
            Lock lock = orderLocks.computeIfAbsent(orderId, k -> new ReentrantLock());
            lock.lock();
            try {
                if (orderId.isEmpty() || orderId == null) {
                    return ResponseEntity.notFound().build(); //add checks for valid orderId
                }

                Order order = orderService.updateOrderStatus(orderId, status);
                return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
            } finally {
                lock.unlock();
            }
        });
    }

    @PutMapping("cancelOrder/{orderId}")
    public CompletableFuture<ResponseEntity<?>> cancelOrder(@PathVariable String orderId) {
        return CompletableFuture.supplyAsync(() -> {
            Lock lock = orderLocks.computeIfAbsent(orderId, k -> new ReentrantLock());
            lock.lock();
            try {
            if (orderId.isEmpty() || orderId == null){
                return ResponseEntity.notFound().build(); //add checks for valid orderId
            }
            Order order = orderService.updateOrderStatus(orderId, CANCEL_ORDER);
            return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
            } finally {
                lock.unlock();
            }
        });
    }
}
