package com.example.test_api.models.dto;

import com.example.test_api.models.projections.MinMaxPriceProjection;
import com.example.test_api.models.projections.ProductWithPriceProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductsWithPriceDto {
    Page<ProductWithPriceProjection> products;
    MinMaxPriceProjection price;
}
