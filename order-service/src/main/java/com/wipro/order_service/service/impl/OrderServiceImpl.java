package com.wipro.order_service.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.wipro.order_service.client.AuthServiceClient;
import com.wipro.order_service.client.CartServiceClient;
import com.wipro.order_service.dto.APIResponse;
import com.wipro.order_service.dto.CartDTO;
import com.wipro.order_service.dto.InventoryUpdate;
import com.wipro.order_service.dto.UserDTO;
import com.wipro.order_service.entity.Order;
import com.wipro.order_service.entity.OrderStatus;
import com.wipro.order_service.exception.APIException;
import com.wipro.order_service.repository.OrderRepository;
import com.wipro.order_service.service.OrderService;
import com.wipro.order_service.service.TransmissionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartServiceClient cartServiceClient;

    @Autowired
    private AuthServiceClient authServiceClient;

    @Autowired
    TransmissionService transmissionService;


    @Transactional
    public Order createOrder(HttpServletRequest request) {

        ResponseEntity<?> authResponseEntity = authServiceClient.getUserByIdOrUsername(getCurrentUserId(), null);

        UserDTO user = transmissionService.sendAndReceive(
                authResponseEntity,
                new TypeReference<>() {
                }
        );

        ResponseEntity<?> cartResponseEntity = cartServiceClient.getCurrentUserCart(request);

        CartDTO cart = transmissionService.sendAndReceive(
                cartResponseEntity,
                new TypeReference<>() {
                }
        );

//        if (cart.getItems().isEmpty()) {
//            throw new OrderException("Cannot create order with empty cart");
//        }

        cartServiceClient.updateCartStatus(cart.getId(), "CHECKOUT", request);

        Order order = new Order();
        order.setUserId(user.getId());
        order.setCartId(cart.getId());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(cart.getTotalAmount());
        order.setShippingAddress(user.getAddress());
        order = orderRepository.save(order);

        sendOrderEvent(order);
        return order;
    }

    private void sendOrderEvent(Order order) {

    }

    @Override
    public List<Order> getOrdersByUserId(String userId) {
        return List.of();
    }

    @Override
    public Order getOrderById(String id) {
        return null;
    }

    @Override
    public Order updateOrderStatus(String orderId, OrderStatus status) {
        return null;
    }

    @Override
    public void processInventoryUpdate(InventoryUpdate update) {

    }

    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }
        throw new SecurityException("User not authenticated");
    }
}