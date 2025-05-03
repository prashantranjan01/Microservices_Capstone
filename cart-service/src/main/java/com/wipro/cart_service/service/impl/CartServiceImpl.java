package com.wipro.cart_service.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.cart_service.client.ProductServiceClient;
import com.wipro.cart_service.dto.APIResponse;
import com.wipro.cart_service.dto.CartDTO;
import com.wipro.cart_service.dto.ProductDTO;
import com.wipro.cart_service.entity.Cart;
import com.wipro.cart_service.entity.CartItem;
import com.wipro.cart_service.entity.CartStatus;
import com.wipro.cart_service.exception.APIException;
import com.wipro.cart_service.exception.CartException;
import com.wipro.cart_service.exception.PermissionDeniedException;
import com.wipro.cart_service.repository.CartItemRepository;
import com.wipro.cart_service.repository.CartRepository;
import com.wipro.cart_service.service.CartService;
import com.wipro.cart_service.service.TransmissionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ProductServiceClient productServiceClient;

    @Autowired
    PermissionService permissionService;

    @Autowired
    TransmissionService transmissionService;

    @Transactional
    @Override
    public CartDTO getCurrentUserCart(HttpServletRequest request) {
        if (!permissionService.hasPermission(request, "VIEW_CART")) {
            throw new PermissionDeniedException();
        }
        String userId = getCurrentUserId();
        Cart cart = getOrCreateCart(userId);
        CartDTO cartDTO = new CartDTO(cart);
        cartDTO.setItems(this.cartItemRepository.findByCartId(cart.getId()));
        cartDTO.setTotalAmount(calculateTotalPrice(cart.getItems()));
        return cartDTO;
    }

    @Transactional
    @Override
    public CartDTO addItemToCurrentUserCart(HttpServletRequest request, CartItem itemDto) {
        if (!permissionService.hasPermission(request, "ADD_TO_CART")) {
            throw new PermissionDeniedException();
        }
        String userId = getCurrentUserId();
        Cart cart = getOrCreateCart(userId);

        ResponseEntity<?> responseEntity = productServiceClient.getProductById(itemDto.getProductId());

        ProductDTO product = transmissionService.sendAndReceive(
                responseEntity,
                new TypeReference<>() {
                }
        );

        if (product.getStockQuantity() <= 0) {
            throw new CartException("Product is out of stock");
        }
        Optional<CartItem> existingItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), itemDto.getProductId());
        CartItem item = new CartItem();
        if (existingItem.isPresent()) {
            item = existingItem.get();
            item.setQuantity(item.getQuantity() + itemDto.getQuantity());
        } else {
            item.setCart(cart);
            item.setId(UUID.randomUUID().toString());
            item.setProductId(itemDto.getProductId());
            item.setPrice(product.getPrice());
            item.setQuantity(itemDto.getQuantity());
        }
        cartItemRepository.save(item);
        return getCurrentUserCart(request);
    }

    @Transactional
    public CartDTO removeItemFromCurrentUserCart(HttpServletRequest request, String productId) {
        if (!permissionService.hasPermission(request, "REMOVE_FROM_CART")) {
            throw new PermissionDeniedException();
        }
        String userId = getCurrentUserId();
        Cart cart = getOrCreateCart(userId);
        cartItemRepository.deleteByCartIdAndProductId(cart.getId(), productId);
        return getCurrentUserCart(request);
    }

    @Transactional
    public void clearCurrentUserCart(HttpServletRequest request) {
        if (!permissionService.hasPermission(request, "REMOVE_FROM_CART")) {
            throw new PermissionDeniedException();
        }
        String userId = getCurrentUserId();
        Cart cart = getOrCreateCart(userId);
        cartItemRepository.deleteByCartId(cart.getId());
    }

    @Override
    public CartDTO updateCartStatus(HttpServletRequest request, String cartId, CartStatus status) {
        if (!permissionService.hasPermission(request, "CHANGE_CART_STATUS")) {
            throw new PermissionDeniedException();
        }
        Optional<Cart> optionalCart = cartRepository.findById(cartId);

        if(optionalCart.isEmpty()){
            throw new CartException("Cart with id : " + cartId + " not found.");
        }
        Cart cart = optionalCart.get();
        cart.setStatus(status);
        Cart savedCart = cartRepository.save(cart);
        CartDTO cartDTO = new CartDTO(savedCart);
        cartDTO.setItems(this.cartItemRepository.findByCartId(cart.getId()));
        cartDTO.setTotalAmount(calculateTotalPrice(cart.getItems()));
        return cartDTO;
    }

    private double calculateTotalPrice(List<CartItem> items) {
        return items.stream().reduce(0.0,
                (price, item) -> price + (item.getQuantity() * item.getPrice()),
                Double::sum);
    }

    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }
        throw new SecurityException("User not authenticated");
    }

    private Cart getOrCreateCart(String userId) {
        return cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setCreatedAt(LocalDateTime.now());
                    cart.setUpdatedAt(LocalDateTime.now());
                    cart.setId(UUID.randomUUID().toString());
                    cart.setUserId(userId);
                    return cartRepository.save(cart);
                });
    }
}