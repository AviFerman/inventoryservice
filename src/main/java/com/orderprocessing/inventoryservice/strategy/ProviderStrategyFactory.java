package com.orderprocessing.inventoryservice.strategy;

public interface ProviderStrategyFactory {
    ProductOrderingStrategy getStrategy(String category);
}
