package com.example.test_api.services.impl;

import com.example.test_api.models.Attribute;
import com.example.test_api.models.AttributeValue;
import com.example.test_api.models.exceptions.ProductAlreadyHasAttribute;
import com.example.test_api.models.input.AttributeInput;
import com.example.test_api.models.input.AttributeValueInput;
import com.example.test_api.repositories.AttributeRepository;
import com.example.test_api.repositories.AttributeValueRepository;
import com.example.test_api.services.AttributeService;
import com.example.test_api.services.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AttributeServiceImpl implements AttributeService {
    private final AttributeRepository attributeRepository;
    private final AttributeValueRepository attributeValueRepository;
    private final ProductService productService;

    public AttributeServiceImpl(AttributeRepository attributeRepository, AttributeValueRepository attributeValueRepository, ProductService productService) {
        this.attributeRepository = attributeRepository;
        this.attributeValueRepository = attributeValueRepository;
        this.productService = productService;
    }

    public Attribute findById(Long id) {
        return attributeRepository.findById(id).orElseThrow();
    }

    @Override
    public Attribute create(Long productId, AttributeInput input) {
        var product = productService.findById(productId);
        if (product.getAttributes().stream().anyMatch(attribute -> attribute.getTitle().equals(input.getTitle()))) {
            throw new ProductAlreadyHasAttribute(input.getTitle());
        }

        var attribute = new Attribute(input.getTitle());
        attribute.getValues().addAll(input.getValues()
                .stream()
                .map(x -> new AttributeValue(x.getValue(), attribute)).toList());
        attribute.setProduct(product);
        return this.attributeRepository.save(attribute);
    }


    public Boolean connectAttributes(Long source, Long target) {
        var attribute1 = this.attributeValueRepository.findById(source).orElseThrow();
        var attribute2 = this.attributeValueRepository.findById(target).orElseThrow();
        var added = attribute1.getConnections().add(attribute2);
        this.attributeValueRepository.saveAll(List.of(attribute1, attribute2));
        return added;
    }

    @Override
    public Boolean disconnectAttributes(Long source, Long target) {
        var attribute1 = this.attributeValueRepository.findById(source).orElseThrow();
        var attribute2 = this.attributeValueRepository.findById(target).orElseThrow();
        var removed1 = attribute1.getConnections().remove(attribute2);
        var removed2 = attribute2.getConnections().remove(attribute1);
        this.attributeValueRepository.saveAll(List.of(attribute1, attribute2));
        return removed1 || removed2;
    }

    @Override
    public List<Attribute> findAllByProductId(Long productId) {
        return this.attributeRepository.findByProduct_Id(productId);
    }

    @Override
    public AttributeValue addValue(Long id, AttributeValueInput input) {
        var attribute = this.findById(id);
        var value = new AttributeValue(input.getValue(), attribute);
        return this.attributeValueRepository.save(value);
    }

    @Override
    public Boolean updateAttributes(Long productId, List<AttributeInput> attributes) {
        attributes.forEach(attr -> this.create(productId, attr));
        return true;
    }

    @Override
    @Transactional
    public Boolean deleteValue(Long valueId) {
        this.attributeValueRepository.deleteConnectionsByAttributeValueId(valueId);
        this.attributeValueRepository.deleteById(valueId);
        return true;
    }

    @Override
    @Transactional
    public Boolean deleteById(Long id) {
        this.attributeRepository.deleteAllConnectionsByAttributeId(id);
        this.attributeValueRepository.deleteAttributeValuesByAttribute_Id(id);
        this.attributeRepository.deleteById(id);
        return true;
    }

}
