package com.example.apigateway.core.service;

import com.example.apigateway.core.dto.request.OrderRequest;
import com.example.apigateway.core.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getAll();
    OrderResponse getById(Long id);
    void delete(Long id);
    OrderResponse save(OrderRequest order);
}
