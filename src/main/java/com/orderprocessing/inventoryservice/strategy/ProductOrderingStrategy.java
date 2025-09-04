package com.orderprocessing.inventoryservice.strategy;

import com.orderprocessing.inventoryservice.dto.Item;
import com.orderprocessing.inventoryservice.dto.ProductInfo;

public interface ProductOrderingStrategy {
    void processOrder(Item item, ProductInfo productInfo);
}
