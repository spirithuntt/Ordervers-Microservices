package com.example.apigateway.core.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Long id;

    private Long productId;
    private int quantity;

    private String orderStatus;
}
