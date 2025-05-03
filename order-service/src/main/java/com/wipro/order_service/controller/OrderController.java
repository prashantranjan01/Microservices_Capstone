package com.wipro.order_service.controller;


import com.wipro.order_service.dto.APIResponse;
import com.wipro.order_service.entity.Order;
import com.wipro.order_service.entity.OrderStatus;
import com.wipro.order_service.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<APIResponse<?>> createOrder(HttpServletRequest request) {
        try {
            Order cart = orderService.createOrder(request);
            APIResponse<Order> apiResponse = new APIResponse<>(HttpStatus.OK, cart);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.NOT_FOUND, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<APIResponse<?>> getOrder(@PathVariable String orderId) {
        try {
            Order order = orderService.getOrderById(orderId);
            APIResponse<Order> apiResponse = new APIResponse<>(HttpStatus.OK, order);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.NOT_FOUND, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<Order>>> getAllOrders(@PathVariable String userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        APIResponse<List<Order>> apiResponse = new APIResponse<>(HttpStatus.OK, orders);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<APIResponse<?>> updateOrderStatus(
            @PathVariable String orderId,
            @RequestParam OrderStatus status) {
        try {
            Order order = orderService.updateOrderStatus(orderId, status);
            APIResponse<Order> apiResponse = new APIResponse<>(HttpStatus.OK, order);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.BAD_REQUEST, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
}