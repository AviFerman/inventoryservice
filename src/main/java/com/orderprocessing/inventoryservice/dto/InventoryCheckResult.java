package com.orderprocessing.inventoryservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class InventoryCheckResult {
    private String orderId;
}
