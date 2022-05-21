package com.example.test_api.controllers;

import com.example.test_api.models.Product;
import com.example.test_api.models.input.AttributeInput;
import com.example.test_api.models.input.ProductInput;
import com.example.test_api.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/products")
@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("{id}")
    Product getProduct(@PathVariable Long id) {
        return this.productService.findById(id);
    }

    @GetMapping
    public Page<Product> productPage(Pageable pageable) {
        return this.productService.findAllPageable(pageable);
    }

    @PostMapping("/create")
    public Product createProduct(@RequestBody ProductInput body) {
        return this.productService.create(body);
    }

    @PostMapping("/{id}/add-attribute")
    public Product addProductAttribute(@PathVariable Long id,
                                       @RequestBody AttributeInput body) {
        return this.productService.addAttribute(id, body);
    }

}
