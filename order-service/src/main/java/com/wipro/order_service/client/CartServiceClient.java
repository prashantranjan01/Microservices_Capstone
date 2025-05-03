package com.wipro.order_service.client;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "cart-service", url = "${cart.service.url}")
public interface CartServiceClient {

    @GetMapping("/api/cart")
    ResponseEntity<?> getCurrentUserCart(HttpServletRequest request);

    @PutMapping("/api/cart/{cardId}/status")
    ResponseEntity<?> updateCartStatus(
            @PathVariable String cartId,
            @RequestParam String status,
            HttpServletRequest request
    );
}

