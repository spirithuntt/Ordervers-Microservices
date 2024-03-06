package com.example.ordermanagementservice.core.service;

import com.example.ordermanagementservice.core.model.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAll();
    Order getById(Long id);
    Order save(Order order);
    Order update(Long id, Order order);
    void delete(Long id);
}
