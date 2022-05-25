package com.example.test_api.services.impl;

import com.example.test_api.models.Product;
import com.example.test_api.models.dto.ProductsWithPriceDto;
import com.example.test_api.models.exceptions.ProductNotFoundException;
import com.example.test_api.models.input.ProductInput;
import com.example.test_api.models.requests.FilterRequest;
import com.example.test_api.repositories.ProductRepository;
import com.example.test_api.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product findById(Long id) {
        return this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Page<Product> findAllPageable(Pageable pageable) {
        return this.productRepository.findAll(pageable);
    }

    @Override
    public Product create(ProductInput input) {
        if (input.getTitle() == null || input.getTitle().isEmpty()) {
            return null;
        }
        return this.productRepository.save(
                new Product(
                        input.getTitle()
                )
        );
    }

    public ProductsWithPriceDto findAllWithPrice(Pageable pageable, FilterRequest filters) {
        Sort sort = Sort.unsorted();
        if (filters.getSortBy().equalsIgnoreCase("price (high)")) {
            sort = Sort.by("minPrice").descending();
        } else if (filters.getSortBy().equalsIgnoreCase("price (low)")) {
            sort = Sort.by("minPrice").ascending();
        } else if (filters.getSortBy().equalsIgnoreCase("name (a - z)")) {
            sort = Sort.by("title").ascending();
        } else if (filters.getSortBy().equalsIgnoreCase("name (z - a)")) {
            sort = Sort.by("title").descending();
        }
        var priceRange = this.productRepository.findMinAndMaxPrice();
        var products = this.productRepository.findAllWithPrice(
                filters.getPriceLow() == -1 ? priceRange.getMinPrice() : filters.getPriceLow(),
                filters.getPriceHigh() == -1 ? priceRange.getMaxPrice() : filters.getPriceHigh(),
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        sort
                ));
        return new ProductsWithPriceDto(products, priceRange);
    }
}
