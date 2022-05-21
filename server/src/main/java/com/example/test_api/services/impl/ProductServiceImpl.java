package com.example.test_api.services.impl;

import com.example.test_api.models.Product;
import com.example.test_api.models.input.AttributeInput;
import com.example.test_api.models.input.ProductInput;
import com.example.test_api.repositories.ProductRepository;
import com.example.test_api.services.AttributeService;
import com.example.test_api.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final AttributeService attributeService;

    public ProductServiceImpl(ProductRepository productRepository, AttributeService attributeService) {
        this.productRepository = productRepository;
        this.attributeService = attributeService;
    }

    @Override
    public Product findById(Long id) {
        return this.productRepository.findById(id).orElseThrow();
    }

    @Override
    public Page<Product> findAllPageable(Pageable pageable) {
        return this.productRepository.findAll(pageable);
    }

    @Override
    public Product create(ProductInput input) {
        return this.productRepository.save(
                new Product(
                        input.getTitle()
                )
        );
    }

    public Product addAttribute(Long productId, AttributeInput attributeInput) {
        var attribute = this.attributeService.create(attributeInput);
        var product = this.findById(productId);
        product.getAttributes().add(attribute);
        return this.productRepository.save(product);
    }
}
