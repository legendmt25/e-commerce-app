package com.example.test_api.services;

import com.example.test_api.models.Attribute;
import com.example.test_api.models.AttributeValue;
import com.example.test_api.models.input.AttributeInput;
import com.example.test_api.models.input.AttributeValueInput;

import java.util.List;

public interface AttributeService {
    Attribute create(Long productId, AttributeInput input);

    Boolean connectAttributes(Long source, Long target);

    Boolean disconnectAttributes(Long source, Long target);

    List<Attribute> findAllByProductId(Long productId);

    AttributeValue addValue(Long id, AttributeValueInput input);

    Boolean updateAttributes(Long productId, List<AttributeInput> attributes);

    Boolean deleteValue(Long valueId);

    Boolean deleteById(Long id);

}
