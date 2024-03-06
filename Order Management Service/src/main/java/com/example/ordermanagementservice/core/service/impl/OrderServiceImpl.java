package com.example.ordermanagementservice.core.service.impl;

import com.example.ordermanagementservice.core.model.entity.Order;
import com.example.ordermanagementservice.core.repository.OrderRepository;
import com.example.ordermanagementservice.core.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order getById(Long id) {
        return findByIdOrThrow(id);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order update(Long id, Order order) {
        order.setId(findByIdOrThrow(id).getId());
        return orderRepository.save(order);
    }

    @Override
    public void delete(Long id) {
        orderRepository.delete(findByIdOrThrow(id));
    }

    private Order findByIdOrThrow(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found with id " + id));
    }
}
