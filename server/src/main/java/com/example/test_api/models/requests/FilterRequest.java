package com.example.test_api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class FilterRequest {
    private String sortBy;
    private Long priceLow;
    private Long priceHigh;
}
