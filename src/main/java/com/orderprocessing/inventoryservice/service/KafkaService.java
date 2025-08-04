package com.orderprocessing.inventoryservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderprocessing.inventoryservice.dto.OrderData;
import com.orderprocessing.inventoryservice.dto.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaService {
    private final RedisService redisService;
    private final ObjectMapper objectMapper;
    private final ProductCatalogService productCatalogService;

    @KafkaListener(topics = "${spring.kafka.topic.order-event}", groupId = "inventory-service")
    public void handleInventoryForOrder(String message) {
        try {
            OrderEvent orderEvent = objectMapper.readValue(message, OrderEvent.class);
            log.info("handleInventoryForOrder:: Received Order Event with ID: {}", orderEvent.getOrderId());
            Optional<OrderData> orderDataOptional = Optional.ofNullable(redisService.readJson("order:" + orderEvent.getOrderId(), OrderData.class));
            OrderData orderData = orderDataOptional.get();
            log.info("handleInventoryForOrder:: Received OrdedData: {}", orderData);
            manageOrder(orderData, orderEvent);
        } catch (JsonProcessingException e) {
            log.error("Error processing inventory check message", e);
        }
    }

    private static void manageOrder(OrderData orderData, OrderEvent orderEvent) {
        if (orderData != null) {
            // Here you would typically check inventory and update it accordingly
            log.info("manageOrder:: Processing inventory for Order ID: {}", orderData.getOrderId());
            // Example: Check product availability in the catalog
            orderData.getItems().forEach(item -> {
                log.info("manageOrder:: Checking inventory for Item with Product ID: {}", item.getProductId());
                // Example: Check if the item is in stock

                if (item.getAvailability() == ItemAvailabilityEnum.IN_STOCK) {
                    log.info("manageOrder:: Item {} is in stock. Quantity: {}", item.getProductId(), item.getQuantity());
                    // Perform further processing for in-stock items
                } else {
                    log.warn("manageOrder:: Item {} is out of stock.", item.getProductId());
                    // Handle out-of-stock scenario
                }
            });
            // Simulate inventory check and update logic
            // For example, check if enough stock is available
            // If sufficient stock, proceed with order fulfillment
            // If not, handle out-of-stock scenario
        } else {
            log.warn("handleInventoryForOrder:: No OrderData found for Order ID: {}", orderEvent.getOrderId());
        }
    }
}
