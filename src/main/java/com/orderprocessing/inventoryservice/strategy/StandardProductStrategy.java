package com.orderprocessing.inventoryservice.strategy;

import com.orderprocessing.inventoryservice.dto.Item;
import com.orderprocessing.inventoryservice.dto.ProductInfo;
import com.orderprocessing.inventoryservice.enums.ItemAvailabilityEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("STANDARD")
@Slf4j
public class StandardProductStrategy implements ProductOrderingStrategy {
    @Override
    public void processOrder(Item item, ProductInfo productInfo) {
        log.info("StandardProductStrategy: processing STANDARD item");
        if (productInfo.getAvailableQuantity() >= item.getQuantity()) {
            productInfo.setAvailableQuantity(productInfo.getAvailableQuantity() - item.getQuantity());
            item.setAvailability(ItemAvailabilityEnum.IN_STOCK);
        } else {
            item.setAvailability(ItemAvailabilityEnum.OUT_OF_STOCK);
        }
    }
}
