package com.example.apigateway.core.controller;

import com.example.apigateway.core.consumer.ProductConsumer;
import com.example.apigateway.core.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gateway/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductConsumer productConsumer;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productConsumer.getAllProducts());
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody String product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productConsumer.createProduct(product));
    }


    @DeleteMapping({"/{productId}"} )
    public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
        productConsumer.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
