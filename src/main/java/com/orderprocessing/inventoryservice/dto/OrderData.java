package com.orderprocessing.inventoryservice.dto;

import com.orderprocessing.inventoryservice.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderData {

    private String orderId;
    private String customerName;
    private ZonedDateTime requestedAt;
    private String correlationId;
    private List<Item> items;
    private OrderStatusEnum orderStatus;
}
