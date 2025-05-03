package com.wipro.order_service.service;

import com.wipro.order_service.entity.Order;
import jakarta.servlet.http.HttpServletRequest;

public interface OrderService {
    public Order createOrder(HttpServletRequest request);
}