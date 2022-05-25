package com.example.test_api.models.projections;

import com.example.test_api.models.Product;

public interface ProductWithPriceProjection {
    Long getId();

    String getTitle();

    Long getMinPrice();

    Long getMaxPrice();
}

