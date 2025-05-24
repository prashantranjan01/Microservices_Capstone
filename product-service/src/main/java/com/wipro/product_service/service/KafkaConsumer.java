//package com.wipro.product_service.service;
//
//import com.wipro.product_service.dto.OrderEvent;
//import com.wipro.product_service.dto.OrderItemEvent;
//import com.wipro.product_service.model.Product;
//import com.wipro.product_service.repository.ProductRepository;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class KafkaConsumer {
//
//    @Autowired
//    ProductRepository productRepository;
//
//    @Autowired
//    KafkaProducer kafkaProducer;
//
//    @KafkaListener(
//            topics = "order-events",
//            groupId = "product-service-group")
//    @Transactional
//    public void consumeOrderEvent(OrderEvent event) {
//        for (OrderItemEvent item : event.getItems()) {
//            Product product = productRepository.findById(item.getProductId())
//                    .orElseThrow(() -> new RuntimeException("Product not found: " + item.getProductId()));
//
//            if (product.getStockQuantity() < item.getQuantity()) {
//                // Not enough stock
//                kafkaProducer.sendInventoryUpdate(
//                        event.getOrderId(),
//                        item.getProductId(),
//                        item.getQuantity(),
//                        false,
//                        "Insufficient stock for product: " + product.getName()
//                );
//                continue;
//            }
//
//            // Update inventory
//            product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
//            productRepository.save(product);
//
//            // Send success update
//            kafkaProducer.sendInventoryUpdate(
//                    event.getOrderId(),
//                    item.getProductId(),
//                    item.getQuantity(),
//                    true,
//                    "Inventory updated successfully"
//            );
//        }
//    }
//}
