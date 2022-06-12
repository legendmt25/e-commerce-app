package com.example.test_api.models.requests;

import com.example.test_api.models.input.AttributeInput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManageAttributesRequest {
    private List<AttributeInput> created;
    private List<AttributeInput> updated;
    private List<AttributeInput> deleted;
}
