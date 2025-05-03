package com.wipro.cart_service.controller;


import com.wipro.cart_service.dto.APIResponse;
import com.wipro.cart_service.dto.CartDTO;
import com.wipro.cart_service.entity.CartItem;
import com.wipro.cart_service.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    CartService cartService;


    @GetMapping
    public ResponseEntity<APIResponse<?>> getCurrentUserCart(HttpServletRequest request) {
        try {
            CartDTO cart = cartService.getCurrentUserCart(request);
            APIResponse<CartDTO> apiResponse = new APIResponse<>(HttpStatus.OK, cart);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.NOT_FOUND, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
    }

    @PostMapping("/items")
    public ResponseEntity<APIResponse<?>> addItemToCart(
            HttpServletRequest request,
            @RequestBody CartItem cartItem) {
        try {
            CartDTO cart = cartService.addItemToCurrentUserCart(request, cartItem);
            APIResponse<CartDTO> apiResponse = new APIResponse<>(HttpStatus.OK, cart);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.NOT_FOUND, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<APIResponse<?>> removeItemFromCart(
            HttpServletRequest request,
            @PathVariable String productId) {
        try {
            CartDTO cart = cartService.removeItemFromCurrentUserCart(request, productId);
            APIResponse<CartDTO> apiResponse = new APIResponse<>(HttpStatus.OK, cart);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.NOT_FOUND, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(HttpServletRequest request) {
        cartService.clearCurrentUserCart(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/checkout")
    public void checkoutCart(HttpServletRequest request) {
        cartService.chekout(request);
    }
}