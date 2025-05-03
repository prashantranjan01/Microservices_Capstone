package com.wipro.cart_service.service;

import com.wipro.cart_service.dto.CartDTO;
import com.wipro.cart_service.entity.CartItem;
import jakarta.servlet.http.HttpServletRequest;

public interface CartService {

    CartDTO getCurrentUserCart(HttpServletRequest request);

    CartDTO addItemToCurrentUserCart(HttpServletRequest request, CartItem cartItem);

    CartDTO removeItemFromCurrentUserCart(HttpServletRequest request, String productId);

    void clearCurrentUserCart(HttpServletRequest request);

    void chekout(HttpServletRequest request);
}