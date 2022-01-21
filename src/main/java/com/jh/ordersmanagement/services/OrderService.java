package com.jh.ordersmanagement.services;

import com.jh.ordersmanagement.constants.Messages;
import com.jh.ordersmanagement.constants.OrderStatus;
import com.jh.ordersmanagement.entities.Order;
import com.jh.ordersmanagement.entities.OrderDetail;
import com.jh.ordersmanagement.entities.Product;
import com.jh.ordersmanagement.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private ProductService productService;

    public List<Order> list() {
        return orderRepository.findAll();
    }

    public Order get(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Messages.ORDER_NOT_FOUND));
    }

    public Order create(Order order) {
        order.setDate(LocalDateTime.now());
        order.setStatus(OrderStatus.pending);
        orderRepository.save(order);
        fillOrderDetails(order);
        order.setOrderDetail(orderDetailService.createMassive(order.getOrderDetail()));
        fillOrder(order);
        orderRepository.save(order);

        return order;
    }

    public Order update(Long id, Order order) {
        Order orderToUpdate = get(id);

        if (order.getDate() != null) {
            orderToUpdate.setDate(order.getDate());
        }

        orderToUpdate.setStatus(order.getStatus());
        orderToUpdate.setClient(order.getClient());
        orderRepository.save(orderToUpdate);
        orderDetailService.deleteMassive(orderToUpdate.getOrderDetail().stream().map(OrderDetail::getId).collect(Collectors.toList()));

        orderToUpdate.setOrderDetail(order.getOrderDetail());
        fillOrderDetails(orderToUpdate);
        orderToUpdate.setOrderDetail(orderDetailService.createMassive(orderToUpdate.getOrderDetail()));
        fillOrder(orderToUpdate);
        orderRepository.save(orderToUpdate);

        return orderToUpdate;
    }

    public void updateStatus(Long id, OrderStatus status) {
        Order orderToUpdate = get(id);
        orderToUpdate.setStatus(status);
        orderRepository.save(orderToUpdate);
    }

    public void fillOrder(Order order) {
        double subTotal = order.getOrderDetail().stream().mapToDouble(OrderDetail::getItemTotal).sum();
        order.setSubTotal(subTotal);
        order.setCouncilTax(subTotal * 0.10);
        order.setCountyTax((subTotal + order.getCouncilTax()) * 0.05);
        order.setStateTax((subTotal + order.getCouncilTax() + order.getCountyTax()) * 0.08);
        order.setFederalTax((subTotal + order.getCouncilTax() + order.getCountyTax() + order.getStateTax()) * 0.02);
        order.setTotalTax(order.getCouncilTax() + order.getCountyTax() + order.getStateTax() + order.getFederalTax());
        order.setTotal(subTotal + order.getTotalTax());
    }

    public void fillOrderDetails(Order order) {
        List<Long> productIds = order.getOrderDetail().stream().map(orderDetail -> orderDetail.getProduct().getId()).collect(Collectors.toList());
        List<Product> products = productService.list(productIds);

        order.getOrderDetail().forEach(orderDetail -> {
            orderDetail.setOrder(order);
            Optional<Product> product = products.stream().filter(p -> Objects.equals(p.getId(), orderDetail.getProduct().getId())).findFirst();
            product.ifPresent(value -> orderDetail.setItemTotal(orderDetail.getQuantity() * value.getUnitPrice()));
        });
    }
}
