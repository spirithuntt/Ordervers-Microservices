package com.example.apigateway.core.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long id;

    @JsonIgnore
    private Long productId;
    private int quantity;

    private String orderStatus;

    private ProductResponse product;
}
