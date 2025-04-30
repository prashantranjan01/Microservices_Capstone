package com.wipro.cart_service.controller;


import com.wipro.cart_service.dto.CartDto;
import com.wipro.cart_service.dto.CartItemDto;
import com.wipro.cart_service.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    CartService cartService;



    @GetMapping("/user/current")
    public ResponseEntity<CartDto> getCurrentUserCart(HttpServletRequest request) {
        return ResponseEntity.ok(cartService.getCurrentUserCart(request));
    }

    @PostMapping("/user/current/items")
    public ResponseEntity<CartDto> addItemToCart(
            HttpServletRequest request,
            @RequestBody CartItemDto itemDto) {
        return ResponseEntity.ok(cartService.addItemToCurrentUserCart(request, itemDto));
    }

    @DeleteMapping("/user/current/items/{productId}")
    public ResponseEntity<CartDto> removeItemFromCart(
            HttpServletRequest request,
            @PathVariable Long productId) {
        return ResponseEntity.ok(cartService.removeItemFromCurrentUserCart(request, productId));
    }

    @DeleteMapping("/user/current/clear")
    public ResponseEntity<Void> clearCart(HttpServletRequest request) {
        cartService.clearCurrentUserCart(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/user/current/checkout")
    public ResponseEntity<CartDto> checkoutCart(HttpServletRequest request) {
        return ResponseEntity.ok(cartService.prepareCurrentUserCartForCheckout(request));
    }
}