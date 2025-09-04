package com.orderprocessing.inventoryservice.dto;

import com.orderprocessing.inventoryservice.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@RedisHash("RedisOrderData")
@AllArgsConstructor
@NoArgsConstructor
public class RedisOrderData implements Serializable {

    @Id
    private String orderId;
    private String customerName;
    private ZonedDateTime requestedAt;
    private String correlationId;
    private List<Item> items;
    private OrderStatusEnum orderStatus;
}
