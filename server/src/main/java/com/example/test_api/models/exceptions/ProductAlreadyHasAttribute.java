package com.example.test_api.models.exceptions;

public class ProductAlreadyHasAttribute extends RuntimeException {
    public ProductAlreadyHasAttribute(String title) {
        super("Product already has attribute with name " + title);
    }
}
