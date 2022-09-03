package com.example.test_api.services;

import com.example.test_api.models.AttributeValue;
import com.example.test_api.models.input.AttributeValueInput;

import java.util.Collection;

public interface AttributeValueService {

    AttributeValue findById(Long id);

    AttributeValue create(Long attributeId, AttributeValueInput attributeValueInput);

    Boolean connect(Long source, Long target);

    Boolean disconnect(Long source, Long target);

    Boolean deleteById(Long id);

    Boolean deleteAllById(Collection<Long> ids);

    void deleteAllByAttributeIds(Collection<Long> ids);

    void flush();
}
