package com.jh.ordersmanagement.repositories;

import com.jh.ordersmanagement.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}