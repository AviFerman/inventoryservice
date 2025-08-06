package com.orderprocessing.inventoryservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderprocessing.inventoryservice.dto.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    @Value("${spring.kafka.topic.inventory-check}")
    private String orderTopic;

    public void sendOrderEvent(OrderEvent orderData) {
        try {
            String message = objectMapper.writeValueAsString(orderData);
            kafkaTemplate.send(orderTopic, orderData.getOrderId(), message)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            log.info("Order sent to topic {}: key={}", orderTopic, orderData.getOrderId());
                        } else {
                            log.error("Failed to send order to Kafka", ex);
                        }
                    });
        } catch (JsonProcessingException e) {
            log.error("Error serializing order data", e);
        }
    }
}
