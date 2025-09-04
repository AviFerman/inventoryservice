package com.orderprocessing.inventoryservice.repository;

import com.orderprocessing.inventoryservice.dto.OrderData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RedisOrderDataRepository extends CrudRepository<OrderData, String> {
    Optional<OrderData> findByOrderId(String orderId);
}
