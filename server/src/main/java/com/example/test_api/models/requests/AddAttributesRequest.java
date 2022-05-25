package com.example.test_api.models.requests;

import com.example.test_api.models.input.AttributeInput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddAttributesRequest {
    List<AttributeInput> attributes;
}
