package com.wipro.order_service.service;

import com.wipro.order_service.dto.InventoryUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @Autowired
    OrderService orderService;

    public KafkaConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(
            topics = "inventory-updates",
            groupId = "order-service-group")
    public void consumeInventoryUpdate(InventoryUpdate update) {
        orderService.processInventoryUpdate(update);
    }
}
