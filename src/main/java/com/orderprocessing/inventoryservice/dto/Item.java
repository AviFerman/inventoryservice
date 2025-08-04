package com.orderprocessing.inventoryservice.dto;

import com.orderprocessing.inventoryservice.enums.CategoryEnum;
import com.orderprocessing.inventoryservice.enums.ItemAvailabilityEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private String productId;
    private Integer quantity;
    private CategoryEnum category;
    private ItemAvailabilityEnum availability;

}
