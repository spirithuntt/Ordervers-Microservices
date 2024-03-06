package com.example.productmanagementservice.core.service;

import com.example.productmanagementservice.core.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();
    Product getById(Long id);
    Product save(Product product);
    Product update(Long id, Product product);
    void delete(Long id);


}
