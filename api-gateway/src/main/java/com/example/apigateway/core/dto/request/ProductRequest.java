package com.example.apigateway.core.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private Long id;

    private String name;
    private String description;
    private double price;
    private int quantityAvailable;

}
