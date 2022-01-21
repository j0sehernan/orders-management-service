package com.jh.ordersmanagement.controllers;

import com.jh.ordersmanagement.constants.OrderStatus;
import com.jh.ordersmanagement.constants.Routes;
import com.jh.ordersmanagement.entities.Order;
import com.jh.ordersmanagement.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = Routes.ORDERS, produces = (MediaType.APPLICATION_JSON_VALUE))
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> list() {
        return orderService.list();
    }

    @GetMapping(Routes.ID)
    public Order get(@PathVariable Long id) {
        return orderService.get(id);
    }

    @PostMapping
    public Order create(@RequestBody Order order) {
        return orderService.create(order);
    }

    @PutMapping(Routes.ID)
    public Order update(@PathVariable Long id, @RequestBody Order order) {
        return orderService.update(id, order);
    }

    @PutMapping(Routes.ID + "/status")
    public void updateStatus(@PathVariable Long id, @RequestBody OrderStatus status) {
        orderService.updateStatus(id, status);
    }
}
