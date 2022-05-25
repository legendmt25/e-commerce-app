package com.example.test_api.models.dto;

import com.example.test_api.models.Attribute;
import com.example.test_api.models.Product;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProductWithAttributesDto {
    private String title;
    private List<Attribute> attributes;
    private List<String> connections;

    public ProductWithAttributesDto(Product product) {
        this.title = product.getTitle();
        this.attributes = product.getAttributes();
        this.connections = this.attributes.stream()
                .flatMap(attr -> attr.getValues().stream())
                .flatMap(value -> value.getConnections().stream().map(conn -> value.getId() + "," + conn.getId()))
                .collect(Collectors.toList());

    }
}