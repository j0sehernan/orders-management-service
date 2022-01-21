package com.jh.ordersmanagement.repositories;

import com.jh.ordersmanagement.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}