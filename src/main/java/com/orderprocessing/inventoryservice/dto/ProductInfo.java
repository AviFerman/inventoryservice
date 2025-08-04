package com.orderprocessing.inventoryservice.dto;

import com.orderprocessing.inventoryservice.enums.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfo {
    private CategoryEnum category;
    private int availableQuantity;
    private LocalDate expirationDate;

    }
