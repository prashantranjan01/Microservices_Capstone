package com.wipro.order_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.order_service.client.AuthServiceClient;
import com.wipro.order_service.client.CartServiceClient;
import com.wipro.order_service.dto.APIResponse;
import com.wipro.order_service.dto.CartDTO;
import com.wipro.order_service.dto.UserDTO;
import com.wipro.order_service.entity.Order;
import com.wipro.order_service.entity.OrderStatus;
import com.wipro.order_service.exception.APIException;
import com.wipro.order_service.exception.OrderException;
import com.wipro.order_service.exception.PermissionDeniedException;
import com.wipro.order_service.repository.OrderRepository;
import com.wipro.order_service.service.OrderService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartServiceClient cartServiceClient;

    @Autowired
    private AuthServiceClient authServiceClient;


    @Transactional
    public Order createOrder(HttpServletRequest request) {

        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<?> authResponseEntity = authServiceClient.getUserByIdOrUsername(getCurrentUserId() , null);

        if (authResponseEntity.getStatusCode() != HttpStatus.OK) {
            throw new APIException("Failed to get user from auth microservice.");
        }
        APIResponse<?> authApiResponse = mapper.convertValue(authResponseEntity.getBody(), APIResponse.class);

        if (authApiResponse.getData() == null) {
            throw new APIException("Failed to get user from auth microservice.");
        }

        UserDTO user = mapper.convertValue(authApiResponse.getData(), UserDTO.class);

        ResponseEntity<?> cartResponseEntity = cartServiceClient.getCurrentUserCart(request);

        if (cartResponseEntity.getStatusCode() != HttpStatus.OK) {
            throw new APIException("Failed to get cart from cart microservice.");
        }
        APIResponse<?> cartApiResponse = mapper.convertValue(cartResponseEntity.getBody(), APIResponse.class);

        if (cartApiResponse.getData() == null) {
            throw new APIException("Failed to get user from auth microservice.");
        }

        CartDTO cart = mapper.convertValue(cartApiResponse.getData(), CartDTO.class);

//        if (cart.getItems().isEmpty()) {
//            throw new OrderException("Cannot create order with empty cart");
//        }

        Order order = new Order();
        order.setUserId(user.getId());
        order.setCartId(cart.getId());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(cart.getTotalAmount());
        order.setShippingAddress(user.getAddress());
        order = orderRepository.save(order);

        cartServiceClient.checkout(request);
        return order;
    }

    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }
        throw new SecurityException("User not authenticated");
    }

//    public OrderDto getOrderById(Long orderId) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new OrderException("Order not found"));
//        return mapToDto(order);
//    }
//
//    public List<OrderDto> getOrdersByUserId(Long userId) {
//        return orderRepository.findByUserId(userId).stream()
//                .map(this::mapToDto)
//                .collect(Collectors.toList());
//    }
//
//    @Transactional
//    public OrderDto updateOrderStatus(Long orderId, OrderStatus status) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new OrderException("Order not found"));
//        order.setStatus(status);
//        order = orderRepository.save(order);
//        return mapToDto(order);
//    }
//
//    private OrderDto mapToDto(Order order) {
//        OrderDto dto = new OrderDto();
//        dto.setId(order.getId());
//        dto.setUserId(order.getUserId());
//        dto.setCartId(order.getCartId());
//        dto.setStatus(order.getStatus());
//        dto.setTotalAmount(order.getTotalAmount());
//        dto.setShippingAddress(order.getShippingAddress());
//        dto.setCreatedAt(order.getCreatedAt());
//        dto.setUpdatedAt(order.getUpdatedAt());
//
//        // Get user details
//        UserDto user = userServiceClient.getUserById(order.getUserId());
//        dto.setUserFullName(user.getfName() + " " + user.getlName());
//
//        // Get order items
//        List<OrderItemDto> itemDtos = order.getItems().stream().map(item -> {
//            OrderItemDto itemDto = new OrderItemDto();
//            itemDto.setProductId(item.getProductId());
//            itemDto.setQuantity(item.getQuantity());
//            itemDto.setPricePerUnit(item.getPricePerUnit());
//
//            // Get product details
//            ProductDto product = productServiceClient.getProductById(item.getProductId());
//            itemDto.setProductName(product.getName());
//
//            return itemDto;
//        }).collect(Collectors.toList());
//
//        dto.setItems(itemDtos);
//        return dto;
//    }
}

//@Service
//public class OrderServiceImpl implements CartService {
//
//    @Autowired
//    CartRepository cartRepository;
//    @Autowired
//    CartItemRepository cartItemRepository;
//    @Autowired
//    ProductServiceClient productServiceClient;
//    @Autowired
//    PermissionService permissionService;
//
//    @Transactional
//    @Override
//    public CartDTO getCurrentUserCart(HttpServletRequest request) {
//        if (!permissionService.hasPermission(request, "VIEW_CART")) {
//            throw new PermissionDeniedException();
//        }
//        String userId = getCurrentUserId();
//        Cart cart = getOrCreateCart(userId);
//        CartDTO cartDTO = new CartDTO(cart);
//        cartDTO.setItems(this.cartItemRepository.findByCartId(cart.getId()));
//        cartDTO.setTotalAmount(calculateTotalPrice(cart.getItems()));
//        return cartDTO;
//    }
//
//    @Transactional
//    @Override
//    public CartDTO addItemToCurrentUserCart(HttpServletRequest request, CartItem itemDto) {
//        if (!permissionService.hasPermission(request, "ADD_TO_CART")) {
//            throw new PermissionDeniedException();
//        }
//        String userId = getCurrentUserId();
//        Cart cart = getOrCreateCart(userId);
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        ResponseEntity<?> responseEntity = productServiceClient.getProductById(itemDto.getProductId());
//        if (responseEntity.getStatusCode() != HttpStatus.OK) {
//            throw new APIException("Failed to get product from product microservice.");
//        }
//        APIResponse apiResponse = mapper.convertValue(responseEntity.getBody(), APIResponse.class);
//
//        if (apiResponse.getData() == null) {
//            throw new APIException("Failed to get product from product microservice.");
//        }
//        ProductDTO product = mapper.convertValue(apiResponse.getData(), ProductDTO.class);
//        if (null == product) {
//            throw new APIException("Failed to get product from product microservice.");
//        }
//        if (product.getStockQuantity() <= 0) {
//            throw new CartException("Product is out of stock");
//        }
//        Optional<CartItem> existingItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), itemDto.getProductId());
//        CartItem item = new CartItem();
//        if (existingItem.isPresent()) {
//            item = existingItem.get();
//            item.setQuantity(item.getQuantity() + itemDto.getQuantity());
//        } else {
//            item.setCart(cart);
//            item.setId(UUID.randomUUID().toString());
//            item.setProductId(itemDto.getProductId());
//            item.setPrice(product.getPrice());
//            item.setQuantity(itemDto.getQuantity());
//        }
//        cartItemRepository.save(item);
//        return getCurrentUserCart(request);
//    }
//
//    @Transactional
//    public CartDTO removeItemFromCurrentUserCart(HttpServletRequest request, String productId) {
//        if (!permissionService.hasPermission(request, "REMOVE_FROM_CART")) {
//            throw new PermissionDeniedException();
//        }
//        String userId = getCurrentUserId();
//        Cart cart = getOrCreateCart(userId);
//        cartItemRepository.deleteByCartIdAndProductId(cart.getId(), productId);
//        return getCurrentUserCart(request);
//    }
//
//    @Transactional
//    public void clearCurrentUserCart(HttpServletRequest request) {
//        if (!permissionService.hasPermission(request, "REMOVE_FROM_CART")) {
//            throw new PermissionDeniedException();
//        }
//        String userId = getCurrentUserId();
//        Cart cart = getOrCreateCart(userId);
//        cartItemRepository.deleteByCartId(cart.getId());
//    }
//
//    @Transactional
//    public CartDTO prepareCurrentUserCartForCheckout(HttpServletRequest request) {
//        if (!permissionService.hasPermission(request, "CHECKOUT")) {
//            throw new PermissionDeniedException();
//        }
//        String userId = getCurrentUserId();
//        Cart cart = getOrCreateCart(userId);
//
//        if (cart.getItems().isEmpty()) {
//            throw new CartException("Cannot checkout with empty cart");
//        }
//
//        cart.setStatus(CartStatus.PENDING_CHECKOUT);
//        Cart savedCart = cartRepository.save(cart);
//        CartDTO cartDTO = new CartDTO(savedCart);
//        cartDTO.setTotalAmount(calculateTotalPrice(cart.getItems()));
//        return cartDTO;
//    }
//
//    private double calculateTotalPrice(List<CartItem> items) {
//        return items.stream().reduce(0.0,
//                (price, item) -> price + (item.getQuantity() * item.getPrice()),
//                Double::sum);
//    }
//
//    private String getCurrentUserId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            return authentication.getName();
//        }
//        throw new SecurityException("User not authenticated");
//    }
//
//    private Cart getOrCreateCart(String userId) {
//        return cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE)
//                .orElseGet(() -> {
//                    Cart cart = new Cart();
//                    cart.setCreatedAt(LocalDateTime.now());
//                    cart.setUpdatedAt(LocalDateTime.now());
//                    cart.setId(UUID.randomUUID().toString());
//                    cart.setUserId(userId);
//                    return cartRepository.save(cart);
//                });
//    }
//}