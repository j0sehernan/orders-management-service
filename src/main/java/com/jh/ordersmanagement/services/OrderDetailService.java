package com.jh.ordersmanagement.services;

import com.jh.ordersmanagement.entities.OrderDetail;
import com.jh.ordersmanagement.repositories.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public List<OrderDetail> createMassive(List<OrderDetail> orderDetails) {
        return orderDetailRepository.saveAll(orderDetails);
    }

    public void deleteMassive(List<Long> ids) {
        orderDetailRepository.deleteAllById(ids);
    }
}
