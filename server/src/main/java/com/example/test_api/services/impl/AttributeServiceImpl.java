package com.example.test_api.services.impl;

import com.example.test_api.models.Attribute;
import com.example.test_api.models.AttributeValue;
import com.example.test_api.models.input.AttributeInput;
import com.example.test_api.repositories.AttributeRepository;
import com.example.test_api.repositories.AttributeValueRepository;
import com.example.test_api.services.AttributeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttributeServiceImpl implements AttributeService {
    private final AttributeRepository attributeRepository;
    private final AttributeValueRepository attributeValueRepository;

    public AttributeServiceImpl(AttributeRepository attributeRepository, AttributeValueRepository attributeValueRepository) {
        this.attributeRepository = attributeRepository;
        this.attributeValueRepository = attributeValueRepository;
    }

    public Attribute findById(Long id) {
        return attributeRepository.findById(id).orElseThrow();
    }

    @Override
    public Attribute create(AttributeInput input) {
        return this.attributeRepository.save(
                new Attribute(
                        input.getTitle(),
                        this.attributeValueRepository.saveAll(
                                input.getValues()
                                        .stream()
                                        .map(x -> new AttributeValue(x.getValue()))
                                        .collect(Collectors.toList())
                        )
                )
        );
    }

    public Boolean connectAttributes(Long attributeId1, Long attributeId2) {
        var attribute1 = this.attributeValueRepository.findById(attributeId1).orElseThrow();
        var attribute2 = this.attributeValueRepository.findById(attributeId2).orElseThrow();
        attribute1.getAttributes().add(attribute2);
        attribute2.getAttributes().add(attribute2);

        this.attributeValueRepository.saveAll(List.of(attribute1, attribute2));
        return true;
    }

}
