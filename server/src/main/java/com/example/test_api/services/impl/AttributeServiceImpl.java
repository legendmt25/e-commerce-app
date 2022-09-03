package com.example.test_api.services.impl;

import com.example.test_api.models.Attribute;
import com.example.test_api.models.AttributeValue;
import com.example.test_api.models.exceptions.ProductAlreadyHasAttribute;
import com.example.test_api.models.input.AttributeInput;
import com.example.test_api.models.input.AttributeValueInput;
import com.example.test_api.repositories.AttributeRepository;
import com.example.test_api.services.AttributeService;
import com.example.test_api.services.AttributeValueService;
import com.example.test_api.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AttributeServiceImpl implements AttributeService {
    private final AttributeRepository attributeRepository;
    private final AttributeValueService attributeValueService;
    private final ProductService productService;

    @Override
    public Attribute findById(Long id) {
        return this.attributeRepository.findById(id).orElseThrow();
    }

    @Override
    public Attribute create(Long productId, AttributeInput input) {
        var product = productService.findById(productId);
        if (product.getAttributes().stream().anyMatch(attribute -> attribute.getTitle().equals(input.getTitle()))) {
            throw new ProductAlreadyHasAttribute(input.getTitle());
        }

        var attribute = new Attribute(input.getTitle(), product);
        attribute.getValues().addAll(input.getValues()
                .stream()
                .map(x -> new AttributeValue(x.getValue(), attribute)).toList());
        attribute.setProduct(product);
        return this.attributeRepository.save(attribute);
    }



    @Override
    public List<Attribute> findAllByProductId(Long productId) {
        return this.attributeRepository.findByProduct_Id(productId);
    }

    @Override
    public AttributeValue addValue(Long id, AttributeValueInput input) {
        return attributeValueService.create(id, input);
    }

    @Override
    public Attribute update(Long id, AttributeInput input) {
        var attribute = this.findById(id);
        input.getValues().stream().map(AttributeValueInput::getValue).forEach(System.out::println);
        System.out.println();

        attribute.setValues(input.getValues().stream().map(value -> {
            if (value.getId() == null)
                return this.attributeValueService.create(attribute.getId(), value);
            try {
                return this.attributeValueService.findById(value.getId());
            } catch (Exception ex) {
                return this.attributeValueService.create(attribute.getId(), value);
            }
        }).collect(Collectors.toList()));
        attribute.setTitle(input.getTitle());

        attribute.getValues().stream().map(AttributeValue::getValue).forEach(System.out::println);
        return attributeRepository.save(attribute);
    }

    @Override
    @Transactional
    @Modifying(flushAutomatically = true)
    public Boolean manageAttributes(Long productId, List<AttributeInput> deleted, List<AttributeInput> updated, List<AttributeInput> created) {
        this.deleteAllById(deleted.stream().map(AttributeInput::getId).collect(Collectors.toList()));
        attributeRepository.flush();
        attributeValueService.flush();
        this.addAttributes(productId, created);
        attributeRepository.flush();
        attributeValueService.flush();
        updated.forEach(attr -> this.update(attr.getId(), attr));
        return true;
    }

    @Override
    public Boolean addAttributes(Long productId, List<AttributeInput> attributes) {
        attributes.forEach(attr -> this.create(productId, attr));
        return true;
    }

    @Transactional
    @Override
    @Modifying(flushAutomatically = true)
    public Boolean deleteById(Long id) {
        this.attributeRepository.deleteAllConnectionsByAttributeIds(Collections.singletonList(id));
        this.attributeValueService.deleteAllByAttributeIds(Collections.singletonList(id));
        this.attributeRepository.deleteAllById(Collections.singletonList(id));
        return true;
    }

    @Transactional
    @Override
    @Modifying(flushAutomatically = true)
    public Boolean deleteAllById(Collection<Long> ids) {
        this.attributeRepository.deleteAllConnectionsByAttributeIds(ids);
        this.attributeValueService.deleteAllByAttributeIds(ids);
        this.attributeRepository.deleteAllById(ids);
        return true;
    }

}
