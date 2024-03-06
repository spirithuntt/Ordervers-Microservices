package com.example.apigateway.core.service.impl;

import com.example.apigateway.core.consumer.OrderConsumer;
import com.example.apigateway.core.consumer.ProductConsumer;
import com.example.apigateway.core.dto.request.OrderRequest;
import com.example.apigateway.core.dto.response.OrderResponse;
import com.example.apigateway.core.dto.response.ProductResponse;
import com.example.apigateway.core.service.OrderService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderConsumer orderConsumer;
    private final ProductConsumer productConsumer;

    @Override
    public List<OrderResponse> getAll() {
        return orderConsumer.getAllOrders();
    }

    @Override
    public OrderResponse getById(Long id) {
        return orderConsumer.getOrderById(id);
    }

    @Override
    public void delete(Long id) {
        orderConsumer.deleteOrder(id);
    }

    @Override
    public OrderResponse save( OrderRequest order) {

        ProductResponse productResponse = productConsumer.getProductById(order.getProductId());
        OrderResponse orderResponse = orderConsumer.createOrder(order);

        orderResponse.setProduct(productResponse);
        return orderResponse;

    }


}
