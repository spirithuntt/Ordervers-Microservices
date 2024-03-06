package com.example.apigateway.core.controller;

import com.example.apigateway.core.consumer.OrderConsumer;
import com.example.apigateway.core.dto.request.OrderRequest;
import com.example.apigateway.core.dto.response.OrderResponse;
import com.example.apigateway.core.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gateway/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> bookOrder() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(order));
    }

    @DeleteMapping({"/{orderId}"})
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.delete(orderId);
        return ResponseEntity.noContent().build();
    }


}
