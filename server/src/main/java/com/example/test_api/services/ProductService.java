package com.example.test_api.services;

import com.example.test_api.models.Product;
import com.example.test_api.models.dto.ProductsWithPriceDto;
import com.example.test_api.models.input.ProductInput;
import com.example.test_api.models.requests.FilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product findById(Long id);

    Page<Product> findAllPageable(Pageable pageable);

    Product create(ProductInput input);

    //Product addAttribute(Long productId, AttributeInput attributeInput);

    ProductsWithPriceDto findAllWithPrice(Pageable pageable, FilterRequest filters);
}
