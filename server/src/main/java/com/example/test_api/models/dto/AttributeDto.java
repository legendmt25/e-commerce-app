package com.example.test_api.models.dto;

import com.example.test_api.models.Attribute;
import com.example.test_api.models.AttributeValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttributeDto {
    private String title;
    private List<AttributeValue> values;

    public AttributeDto(Attribute attribute) {
        this.title = attribute.getTitle();
        this.values = attribute.getValues();
    }
}
