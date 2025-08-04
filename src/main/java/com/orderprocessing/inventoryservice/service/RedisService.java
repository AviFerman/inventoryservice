package com.orderprocessing.inventoryservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderprocessing.inventoryservice.dto.OrderData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public void writeJson(String key, Object value) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(value);
        redisTemplate.opsForValue().set(key, json);
        log.debug("Wrote to Redis - key: {}, value: {}", key, json);
    }

    public OrderData readJson(String key, Class<OrderData> type) {
        try {
            String json = redisTemplate.opsForValue().get(key);
            if (json == null) {
                log.debug("No value found in Redis for key: {}", key);
                return null;
            }
            OrderData orderData = objectMapper.readValue(json, OrderData.class);
            log.debug("Read from Redis - key: {}, value: {}", key, orderData);
            return orderData;
        } catch (JsonProcessingException e) {
            log.error("Error deserializing JSON from Redis for key: {}", key, e);
            return null;
        }
    }
}
