package com.wipro.order_service.client;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "cart-service", url = "${cart.service.url}")
public interface CartServiceClient {

    @GetMapping("/api/cart")
    ResponseEntity<?> getCurrentUserCart(HttpServletRequest request);

    @PostMapping("/api/cart/checkout")
    void checkout(HttpServletRequest request);
}

