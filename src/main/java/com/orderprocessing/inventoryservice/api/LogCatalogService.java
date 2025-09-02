package com.orderprocessing.inventoryservice.api;

import com.orderprocessing.inventoryservice.dto.ProductInfo;
import com.orderprocessing.inventoryservice.service.ProductCatalogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@Service
@AllArgsConstructor
@RestController
@Slf4j
public class LogCatalogService {
    private final ProductCatalogService productCatalogService;

    @GetMapping("/api/products/catalog")
    public Map<String, ProductInfo> getProductCatalog() {
        log.info("Current product catalog: {}", productCatalogService);
        return productCatalogService.getAllProducts();
    }
}

