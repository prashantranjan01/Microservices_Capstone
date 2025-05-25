package com.wipro.order_service.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.wipro.order_service.client.AuthServiceClient;
import com.wipro.order_service.client.CartServiceClient;
import com.wipro.order_service.dto.*;
import com.wipro.order_service.entity.Order;
import com.wipro.order_service.entity.OrderItem;
import com.wipro.order_service.entity.OrderStatus;
import com.wipro.order_service.exception.OrderException;
import com.wipro.order_service.repository.OrderRepository;
import com.wipro.order_service.service.KafkaProducer;
import com.wipro.order_service.service.OrderService;
import com.wipro.order_service.service.TransmissionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Autowired
    KafkaProducer kafkaProducer;


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

        if (cart.getItems().isEmpty()) {
            throw new OrderException("Cannot create order with empty cart");
        }

        cartServiceClient.updateCartStatus(cart.getId(), "CHECKOUT", request);

        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setUserId(user.getId());
        order.setCartId(cart.getId());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(cart.getTotalAmount());
        order.setShippingAddress(user.getAddress());

        Order finalOrder = order;
        List<OrderItem> orderItems = cart.getItems().stream().map(cartItem -> {
            OrderItem item = new OrderItem();
            item.setOrder(finalOrder);
            item.setProductId(cartItem.getProductId());
            item.setQuantity(cartItem.getQuantity());
            item.setPricePerUnit(cartItem.getPrice());
            return item;
        }).collect(Collectors.toList());

        order.setItems(orderItems);
        order = orderRepository.save(order);

//        sendOrderEvent(order);
        return order;
    }

//    private void sendOrderEvent(Order order) {
//        OrderEvent event = new OrderEvent();
//        event.setOrderId(order.getId());
//        event.setUserId(order.getUserId());
//        event.setTotalAmount(order.getTotalAmount());
//
//        event.setItems(order.getItems().stream().map(item -> {
//            OrderItemEvent itemEvent = new OrderItemEvent();
//            itemEvent.setProductId(item.getProductId());
//            itemEvent.setQuantity(item.getQuantity());
//            itemEvent.setPricePerUnit(item.getPricePerUnit());
//            return itemEvent;
//        }).collect(Collectors.toList()));
//
//        kafkaProducer.sendOrderEvent(event);
//    }

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
        Order order = orderRepository.findById(update.getOrderId())
                .orElseThrow(() -> new OrderException("Order not found"));

        if (update.isSuccess()) {
            order.setStatus(OrderStatus.PROCESSING);
        } else {
            order.setStatus(OrderStatus.CANCELLED);
        }

        orderRepository.save(order);
    }

    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }
        throw new SecurityException("User not authenticated");
    }
}