package com.example.apigateway.core.consumer;

import com.example.apigateway.core.dto.request.OrderRequest;
import com.example.apigateway.core.dto.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderConsumer {
    private final RestTemplate restTemplate;
    private final String orderServiceUrl = "http://order-management-service/api/v1/orders";

    public List<OrderResponse> getAllOrders() {
        ResponseEntity<List<OrderResponse>> response = restTemplate.exchange(
                orderServiceUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    public OrderResponse getOrderById(Long orderId) {
        return restTemplate.getForObject(orderServiceUrl + "/" + orderId, OrderResponse.class);
    }

    public OrderResponse createOrder(OrderRequest order) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OrderRequest> entity = new HttpEntity<>(order, headers);
        return restTemplate.postForObject(orderServiceUrl, entity, OrderResponse.class);
    }

    public OrderResponse updateOrder(Long orderId, OrderResponse order) {
        restTemplate.put(orderServiceUrl + "/" + orderId, order);
        return getOrderById(orderId);
    }

    public void deleteOrder(Long orderId) {
        restTemplate.delete(orderServiceUrl + "/" + orderId);
    }

}
