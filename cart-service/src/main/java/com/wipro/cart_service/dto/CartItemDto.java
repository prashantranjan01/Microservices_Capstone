package com.wipro.cart_service.dto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private Long productId;
    private Integer quantity;
    private String productName;
    private BigDecimal price;
}