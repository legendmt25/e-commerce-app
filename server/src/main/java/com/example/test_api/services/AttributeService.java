package com.example.test_api.services;

import com.example.test_api.models.Attribute;
import com.example.test_api.models.input.AttributeInput;

public interface AttributeService {
    Attribute create(AttributeInput input);

    public Boolean connectAttributes(Long attributeId1, Long attributeId2);
}
