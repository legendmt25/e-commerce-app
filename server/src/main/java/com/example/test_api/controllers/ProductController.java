package com.example.test_api.controllers;

import com.example.test_api.models.Attribute;
import com.example.test_api.models.Product;
import com.example.test_api.models.dto.ProductWithAttributesDto;
import com.example.test_api.models.dto.ProductsWithPriceDto;
import com.example.test_api.models.input.AttributeInput;
import com.example.test_api.models.input.ProductInput;
import com.example.test_api.models.projections.ProductWithPriceProjection;
import com.example.test_api.models.requests.AddAttributesRequest;
import com.example.test_api.models.requests.FilterRequest;
import com.example.test_api.models.requests.ManageAttributesRequest;
import com.example.test_api.services.AttributeService;
import com.example.test_api.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/products")
@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final AttributeService attributeService;

    @GetMapping("/{id}")
    ProductWithAttributesDto getProduct(@PathVariable Long id) {
        return new ProductWithAttributesDto(this.productService.findById(id));
    }

    @PostMapping
    public ProductsWithPriceDto productPage(@RequestBody FilterRequest filters, Pageable pageable) {
        return this.productService.findAllWithPrice(pageable, filters);
    }

    @PostMapping("/create")
    public Product createProduct(@RequestBody ProductInput body) {
        return this.productService.create(body);
    }

    @PostMapping("/{id}/add-attribute")
    public Attribute addProductAttribute(@PathVariable Long id,
                                         @RequestBody AttributeInput body) {
        return this.attributeService.create(id, body);
    }

    @PostMapping("/{id}/add-attributes")
    public Boolean addProductAttributes(@PathVariable Long id,
                                        @RequestBody AddAttributesRequest body) {
        return this.attributeService.addAttributes(id, body.getAttributes());
    }

    @PostMapping("/{id}/manage-attributes")
    public Boolean manageAttributes(@PathVariable Long id, @RequestBody ManageAttributesRequest body) {
        return this.attributeService.manageAttributes(id, body.getDeleted(), body.getUpdated(), body.getCreated());
    }


    @GetMapping("/{id}/attributes")
    public List<Attribute> productAttributes(@PathVariable Long id) {
        return this.attributeService.findAllByProductId(id);
    }

}
