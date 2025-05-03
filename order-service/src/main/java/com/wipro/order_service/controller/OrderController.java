package com.wipro.order_service.controller;


import com.wipro.order_service.dto.APIResponse;
import com.wipro.order_service.entity.Order;
import com.wipro.order_service.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

//    @GetMapping("/{orderId}")
//    public ResponseEntity<Order> getOrder(@PathVariable String orderId) {
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