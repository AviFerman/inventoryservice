package com.orderprocessing.inventoryservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderprocessing.inventoryservice.dto.InventoryCheckResult;
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

    @KafkaListener(topics = "${spring.kafka.topic.order-event}", groupId = "inventory-service")
    public void handleInventoryCheckResult(String message) {
        try {
            OrderEvent result = objectMapper.readValue(message, OrderEvent.class);
            log.info("handleInventoryCheckResult:: Received Order Event with ID: {}", result.getOrderId());
            Optional<OrderData> orderData = Optional.ofNullable(redisService.readJson("order:" + result.getOrderId(), OrderData.class));
            log.info("Updated inventory check result for order: {}", orderData.get());
        } catch (JsonProcessingException e) {
            log.error("Error processing inventory check message", e);
        }
    }
}
