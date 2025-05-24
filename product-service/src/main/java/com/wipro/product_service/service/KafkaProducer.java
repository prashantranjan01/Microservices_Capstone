//package com.wipro.product_service.service;
//
//import com.wipro.product_service.dto.InventoryUpdate;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class KafkaProducer {
//    private final KafkaTemplate<String, InventoryUpdate> kafkaTemplate;
//
//    public KafkaProducer(KafkaTemplate<String, InventoryUpdate> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    public void sendInventoryUpdate(String orderId, String productId,
//                                    int quantityChange, boolean success, String message) {
//        InventoryUpdate update = new InventoryUpdate();
//        update.setOrderId(orderId);
//        update.setProductId(productId);
//        update.setQuantityChange(quantityChange);
//        update.setSuccess(success);
//        update.setMessage(message);
//        kafkaTemplate.send("inventory-updates", update);
//    }
//}
