package com.example.productmanagementservice.core.service.impl;

import com.example.productmanagementservice.core.entity.Product;
import com.example.productmanagementservice.core.repository.ProductRepository;
import com.example.productmanagementservice.core.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(Long id) {
        return findByIdOrThrow(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Long id, Product product) {
        product.setId(findByIdOrThrow(id).getId());
        return productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        productRepository.delete(findByIdOrThrow(id));
    }

    private Product findByIdOrThrow(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }
}