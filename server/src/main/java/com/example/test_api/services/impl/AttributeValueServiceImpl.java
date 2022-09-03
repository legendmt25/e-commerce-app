package com.example.test_api.services.impl;

import com.example.test_api.models.AttributeValue;
import com.example.test_api.models.input.AttributeValueInput;
import com.example.test_api.repositories.AttributeRepository;
import com.example.test_api.repositories.AttributeValueRepository;
import com.example.test_api.services.AttributeValueService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class AttributeValueServiceImpl implements AttributeValueService {
    private final AttributeValueRepository attributeValueRepository;
    private final AttributeRepository attributeRepository;

    @Override
    public AttributeValue findById(Long id) {
        return this.attributeValueRepository.findById(id).orElseThrow();
    }

    @Override
    public AttributeValue create(Long attributeId, AttributeValueInput input) {
        var attribute = this.attributeRepository.findById(attributeId).orElseThrow();
        var value = new AttributeValue(input.getValue(), attribute);
        return this.attributeValueRepository.save(value);
    }

    @Override
    public Boolean connect(Long source, Long target) {
        var attribute1 = this.attributeValueRepository.findById(source).orElseThrow();
        var attribute2 = this.attributeValueRepository.findById(target).orElseThrow();
        var added = attribute1.getConnections().add(attribute2);
        this.attributeValueRepository.saveAll(List.of(attribute1, attribute2));
        return added;
    }

    @Override
    public Boolean disconnect(Long source, Long target) {
        var attribute1 = this.attributeValueRepository.findById(source).orElseThrow();
        var attribute2 = this.attributeValueRepository.findById(target).orElseThrow();
        var removed1 = attribute1.getConnections().remove(attribute2);
        var removed2 = attribute2.getConnections().remove(attribute1);
        this.attributeValueRepository.saveAll(List.of(attribute1, attribute2));
        return removed1 || removed2;
    }

    @Override
    @Transactional
    public Boolean deleteById(Long id) {
        this.attributeValueRepository.deleteConnectionsByAttributeValueId(Collections.singletonList(id));
        this.attributeValueRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public Boolean deleteAllById(Collection<Long> ids) {
        this.attributeValueRepository.deleteConnectionsByAttributeValueId(ids);
        this.attributeValueRepository.deleteAllById(ids);
        return true;
    }

    @Override
    public void deleteAllByAttributeIds(Collection<Long> ids) {
        this.attributeValueRepository.deleteByAttribute_IdIsIn(ids);
    }

    @Override
    public void flush() {
        this.attributeValueRepository.flush();
    }
}
