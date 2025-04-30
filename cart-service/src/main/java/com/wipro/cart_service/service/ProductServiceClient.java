package com.wipro.cart_service.service;

import com.wipro.cart_service.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "product-service", url = "${product.service.url}")
public interface ProductServiceClient {

    @GetMapping("/api/product/{id}")
    ResponseEntity<ProductDto> getProductById(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long id);
}

