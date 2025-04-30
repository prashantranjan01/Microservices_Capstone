package com.wipro.cart_service.service;


import com.wipro.cart_service.dto.CartDto;
import com.wipro.cart_service.dto.CartItemDto;
import com.wipro.cart_service.dto.ProductDto;
import com.wipro.cart_service.entity.Cart;
import com.wipro.cart_service.entity.CartItem;
import com.wipro.cart_service.entity.CartStatus;
import com.wipro.cart_service.exception.CartException;
import com.wipro.cart_service.exception.PermissionDeniedException;
import com.wipro.cart_service.repository.CartItemRepository;
import com.wipro.cart_service.repository.CartRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductServiceClient productServiceClient;
    private final PermissionService permissionService;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       ProductServiceClient productServiceClient,
                       PermissionService permissionService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productServiceClient = productServiceClient;
        this.permissionService = permissionService;
    }

    @Transactional
    public CartDto getCurrentUserCart(HttpServletRequest request) {
        if (!permissionService.hasPermission(request, "VIEW_CART")) {
            throw new PermissionDeniedException("Insufficient permissions");
        }
        String userId = getCurrentUserId();
        Cart cart = getOrCreateCart(userId);
        String authHeader = request.getHeader("Authorization");
        return mapToDto(cart , authHeader);
    }

    @Transactional
    public CartDto addItemToCurrentUserCart(HttpServletRequest request, CartItemDto itemDto) {
        if (!permissionService.hasPermission(request, "ADD_TO_CART")) {
            throw new PermissionDeniedException("Insufficient permissions");
        }
        String userId = getCurrentUserId();
        Cart cart = getOrCreateCart(userId);

        // Verify product exists and is available
        String authHeader = request.getHeader("Authorization");

        ProductDto product = productServiceClient.getProductById(authHeader,itemDto.getProductId()).getBody();
        if (null != product && product.getStockQuantity() <= 0) {
            throw new CartException("Product is out of stock");
        }

        Optional<CartItem> existingItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), itemDto.getProductId());

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + itemDto.getQuantity());
            cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProductId(itemDto.getProductId());
            newItem.setQuantity(itemDto.getQuantity());
            cartItemRepository.save(newItem);
        }

        return getCurrentUserCart(request);
    }

    @Transactional
    public CartDto removeItemFromCurrentUserCart(HttpServletRequest request, Long productId) {
        if (!permissionService.hasPermission(request, "REMOVE_FROM_CART")) {
            throw new PermissionDeniedException("Insufficient permissions");
        }
        String userId = getCurrentUserId();
        Cart cart = getOrCreateCart(userId);
        cartItemRepository.deleteByCartIdAndProductId(cart.getId(), productId);
        return getCurrentUserCart(request);
    }

    @Transactional
    public void clearCurrentUserCart(HttpServletRequest request) {
        if (!permissionService.hasPermission(request, "REMOVE_FROM_CART")) {
            throw new PermissionDeniedException("Insufficient permissions");
        }
        String userId = getCurrentUserId();
        Cart cart = getOrCreateCart(userId);
        cartItemRepository.deleteByCartId(cart.getId());
    }

    @Transactional
    public CartDto prepareCurrentUserCartForCheckout(HttpServletRequest request) {
        if (!permissionService.hasPermission(request, "CHECKOUT")) {
            throw new PermissionDeniedException("Insufficient permissions");
        }
        String userId = getCurrentUserId();
        Cart cart = getOrCreateCart(userId);

        // Validate cart before checkout
        if (cart.getItems().isEmpty()) {
            throw new CartException("Cannot checkout with empty cart");
        }

        // Verify all products are still available
        String authHeader = request.getHeader("Authorization");
        for (CartItem item : cart.getItems()) {
            ProductDto product = productServiceClient.getProductById(authHeader, item.getProductId()).getBody();
            if (null != product && product.getStockQuantity() < item.getQuantity()) {
                throw new CartException("Not enough stock for product: " + product.getName());
            }
        }

        cart.setStatus(CartStatus.PENDING_CHECKOUT);
        cartRepository.save(cart);

        return mapToDto(cart , authHeader);
    }

    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return  authentication.getName();
        }
        throw new SecurityException("User not authenticated");
    }

    private Cart getOrCreateCart(String userId) {
        return cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    return cartRepository.save(newCart);
                });
    }

    private CartDto mapToDto(Cart cart , String authHeader) {
        CartDto dto = new CartDto();
        dto.setId(cart.getId());
        dto.setCreatedAt(cart.getCreatedAt());
        dto.setUpdatedAt(cart.getUpdatedAt());
        dto.setUserId(cart.getUserId());
        dto.setStatus(cart.getStatus());
        List<CartItemDto> items = new ArrayList<>();

        if(null != cart.getItems()){
            items = cart.getItems().stream().map(item -> {
                ProductDto product = productServiceClient.getProductById(authHeader,item.getProductId()).getBody();

                CartItemDto itemDto = new CartItemDto();
                itemDto.setProductId(item.getProductId());
                itemDto.setQuantity(item.getQuantity());
                if(null != product){
                    itemDto.setProductName(product.getName());
                    itemDto.setPrice(product.getPrice());
                }
                return itemDto;
            }).collect(Collectors.toList());
        }
        dto.setItems(items);
        dto.setTotalAmount(calculateTotal(items));
        return dto;
    }

    private double calculateTotal(List<CartItemDto> items) {
        return items.stream()
                .mapToDouble(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())).doubleValue())
                .sum();
    }
}