package com.orderprocessing.inventoryservice.service;

import com.orderprocessing.inventoryservice.dto.ProductInfo;
import com.orderprocessing.inventoryservice.enums.CategoryEnum;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

@Service
public class ProductCatalogService {
    private final Map<String, ProductInfo> productCatalog;

    public ProductCatalogService() {
        this.productCatalog = initializeProductCatalog();
    }

    private Map<String, ProductInfo> initializeProductCatalog() {
        return Map.ofEntries(
                Map.entry("P1001", new ProductInfo(CategoryEnum.STANDARD, 10, null)),
                Map.entry("P1002", new ProductInfo(CategoryEnum.PERISHABLE, 15, LocalDate.of(2025, 7, 1))),
                Map.entry("P1003", new ProductInfo(CategoryEnum.DIGITAL, 0, null)),
                Map.entry("P1004", new ProductInfo(CategoryEnum.STANDARD, 25, null)),
                Map.entry("P1005", new ProductInfo(CategoryEnum.PERISHABLE, 8, LocalDate.of(2024, 12, 31))),
                Map.entry("P1006", new ProductInfo(CategoryEnum.DIGITAL, 0, null)),
                Map.entry("P1007", new ProductInfo(CategoryEnum.STANDARD, 50, null)),
                Map.entry("P1008", new ProductInfo(CategoryEnum.PERISHABLE, 12, LocalDate.of(2024, 10, 15))),
                Map.entry("P1009", new ProductInfo(CategoryEnum.DIGITAL, 0, null)),
                Map.entry("P1010", new ProductInfo(CategoryEnum.STANDARD, 30, null))
        );
    }

    public ProductInfo getProduct(String productId) {
        return productCatalog.get(productId);
    }
}
