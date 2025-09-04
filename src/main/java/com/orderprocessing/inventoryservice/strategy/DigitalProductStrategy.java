package com.orderprocessing.inventoryservice.strategy;

import com.orderprocessing.inventoryservice.dto.Item;
import com.orderprocessing.inventoryservice.dto.ProductInfo;
import com.orderprocessing.inventoryservice.enums.ItemAvailabilityEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("DIGITAL")
public class DigitalProductStrategy implements ProductOrderingStrategy {
    @Override
    public void processOrder(Item item, ProductInfo productInfo) {
        log.info("DigitalProductStrategy: processing DIGITAL item");
        if (productInfo.getAvailableQuantity() >= item.getQuantity()) {
            productInfo.setAvailableQuantity(productInfo.getAvailableQuantity() - item.getQuantity());
            item.setAvailability(ItemAvailabilityEnum.IN_STOCK);
        } else {
            item.setAvailability(ItemAvailabilityEnum.OUT_OF_STOCK);
        }
    }
}
