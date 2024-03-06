package com.example.apigateway.core.consumer;

import com.example.apigateway.core.dto.response.ProductResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductConsumer {
    private final RestTemplate restTemplate;
    private final String productServiceUrl = "http://product-management-service/api/v1/products";


    public List<ProductResponse> getAllProducts() {
        ResponseEntity<List<ProductResponse>> response = restTemplate.exchange(
                productServiceUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    public ProductResponse getProductById(@NonNull Long productId) {
        return restTemplate.getForObject(productServiceUrl + "/" + productId, ProductResponse.class);
    }

    public ProductResponse createProduct(String order) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(order, headers);
        return restTemplate.postForObject(productServiceUrl, entity, ProductResponse.class);
    }

    public ProductResponse updateProduct(@NonNull Long productId, ProductResponse product) {
        restTemplate.put(productServiceUrl + "/" + productId, product);
        return getProductById(productId);
    }

    public void deleteProduct(String productId) {
        restTemplate.delete(productServiceUrl + "/" + productId);
    }

}
