package com.example.test_api.services;

import com.example.test_api.models.Product;
import com.example.test_api.models.input.AttributeInput;
import com.example.test_api.models.input.ProductInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product findById(Long id);
    Page<Product> findAllPageable(Pageable pageable);
    Product create(ProductInput input);
    Product addAttribute(Long productId, AttributeInput attributeInput);
}
