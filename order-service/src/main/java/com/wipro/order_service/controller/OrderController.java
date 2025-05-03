package com.wipro.order_service.controller;


import com.wipro.order_service.entity.Order;
import com.wipro.order_service.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<Order> createOrder(HttpServletRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

//    @GetMapping("/{orderId}")
//    public ResponseEntity<Order> getOrder(@PathVariable Long orderId) {
//        return ResponseEntity.ok(orderService.getOrderById(orderId));
//    }
//
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<OrderDto>> getUserOrders(@PathVariable Long userId) {
//        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
//    }
//
//    @PutMapping("/{orderId}/status")
//    public ResponseEntity<OrderDto> updateOrderStatus(
//            @PathVariable Long orderId,
//            @RequestParam OrderStatus status) {
//        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
//    }
}