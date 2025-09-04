package com.orderprocessing.inventoryservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderprocessing.inventoryservice.dto.OrderData;
import com.orderprocessing.inventoryservice.dto.OrderEvent;
import com.orderprocessing.inventoryservice.enums.ItemAvailabilityEnum;
import com.orderprocessing.inventoryservice.enums.OrderStatusEnum;
import com.orderprocessing.inventoryservice.repository.RedisOrderDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceListener {
    private final RedisService redisService;
    private final ObjectMapper objectMapper;
    private final ProductCatalogService productCatalogService;
    private final KafkaProducerService kafkaProducerService;
    private final RedisOrderDataRepository redisOrderDataRepository;

    @KafkaListener(topics = "${spring.kafka.topic.order-event}", groupId = "inventory-service")
    public void handleInventoryForOrder(String message) {
        try {
            OrderEvent orderEvent = objectMapper.readValue(message, OrderEvent.class);
            log.info("handleInventoryForOrder:: Received Order Event with ID: {}", orderEvent.getOrderId());
            OrderData orderData = redisOrderDataRepository.findById(orderEvent.getOrderId())
                    .orElseThrow(() -> new IllegalStateException("Order data not found for ID: " + orderEvent.getOrderId()));
            log.info("handleInventoryForOrder:: Received OrdedData: {}", orderData);
            manageOrder(orderData, orderEvent);
            updateOrderData(orderEvent.getOrderId(), orderData);
            log.info("handleInventoryForOrder:: send Order Id to topic");
            kafkaProducerService.sendOrderEvent(orderEvent);
        } catch (Exception e) {
            log.error("Error processing inventory check message", e);
        }
    }

    private void updateOrderData(String orderId, OrderData orderData) throws JsonProcessingException {
        redisService.updateJson(orderId, orderData);
    }

    private void manageOrder(OrderData orderData, OrderEvent orderEvent) {
        if (orderData != null) {
            log.info("manageOrder:: Processing inventory for Order ID: {}", orderData.getOrderId());
            if (!allPoductIdAreValid(orderData)) {
                log.info("manageOrder:: Invalid Product IDs found in Order Data - cancelling order.");
                orderData.setOrderStatus(OrderStatusEnum.CANCELLED);
                return;
            }

            orderData.getItems().forEach(item -> {
                log.info("manageOrder:: Checking inventory for Item with Product ID: {}", item.getProductId());
                productCatalogService.itemOrder(item);
            });
            if (orderData.getItems().stream()
                    .anyMatch(item -> item.getAvailability() == ItemAvailabilityEnum.IN_STOCK)) {
                orderData.setOrderStatus(OrderStatusEnum.GOOD_TO_GO);

            } else {
                log.info("manageOrder:: No items in stock for Order ID: {}", orderData.getOrderId());
                orderData.setOrderStatus(OrderStatusEnum.CANCELLED);
            }


        } else {
            log.warn("handleInventoryForOrder:: No OrderData found for Order ID: {}", orderEvent.getOrderId());
        }
    }

    private boolean allPoductIdAreValid(OrderData orderData) {
        return orderData.getItems().stream()
                .allMatch(item -> productCatalogService.getProduct(item.getProductId()) != null);
    }
}
