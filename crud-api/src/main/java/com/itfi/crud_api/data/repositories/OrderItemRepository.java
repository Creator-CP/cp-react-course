package com.itfi.crud_api.data.repositories;

import com.itfi.crud_api.data.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {}
