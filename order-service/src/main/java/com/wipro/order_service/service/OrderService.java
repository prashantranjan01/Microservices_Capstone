package com.wipro.order_service.service;

import com.wipro.order_service.dto.InventoryUpdate;
import com.wipro.order_service.entity.Order;
import com.wipro.order_service.entity.OrderStatus;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface OrderService {
    Order createOrder(HttpServletRequest request);
    List<Order> getOrdersByUserId(String userId);
    Order getOrderById(String id);
    Order updateOrderStatus(String orderId , OrderStatus status);
    void processInventoryUpdate(InventoryUpdate update);
}