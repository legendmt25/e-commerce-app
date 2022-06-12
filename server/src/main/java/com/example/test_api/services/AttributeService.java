package com.example.test_api.services;

import com.example.test_api.models.Attribute;
import com.example.test_api.models.AttributeValue;
import com.example.test_api.models.input.AttributeInput;
import com.example.test_api.models.input.AttributeValueInput;

import java.util.List;

public interface AttributeService {
    Attribute findById(Long id);

    Attribute create(Long productId, AttributeInput input);

    Attribute update(Long id, AttributeInput input);

    List<Attribute> findAllByProductId(Long productId);

    AttributeValue addValue(Long id, AttributeValueInput input);

    Boolean manageAttributes(Long productId, List<AttributeInput> deleted, List<AttributeInput> updated, List<AttributeInput> created);

    Boolean addAttributes(Long productId, List<AttributeInput> attributes);

    Boolean deleteById(Long id);

}
