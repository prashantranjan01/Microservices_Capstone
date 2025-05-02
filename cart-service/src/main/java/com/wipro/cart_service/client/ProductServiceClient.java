package com.wipro.cart_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "${product.service.url}")
public interface ProductServiceClient {

    @GetMapping("/api/product/{id}")
    ResponseEntity<?> getProductById(@PathVariable String id);
}

