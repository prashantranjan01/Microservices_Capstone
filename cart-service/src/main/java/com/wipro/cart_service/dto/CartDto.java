package com.wipro.cart_service.dto;


import com.wipro.cart_service.entity.CartStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CartDto {
    private Long id;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CartStatus status;
    private List<CartItemDto> items;
    private double totalAmount;
}