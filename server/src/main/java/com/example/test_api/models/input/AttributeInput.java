package com.example.test_api.models.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttributeInput {
    private String title;
    private List<AttributeValueInput> values;
}
