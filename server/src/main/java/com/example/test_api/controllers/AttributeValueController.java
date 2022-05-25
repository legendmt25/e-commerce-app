package com.example.test_api.controllers;

import com.example.test_api.services.AttributeService;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/v1/attribute-value")
@RestController
@CrossOrigin("*")
public class AttributeValueController {
    private final AttributeService attributeService;

    public AttributeValueController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @DeleteMapping("/{id}/delete")
    public Boolean deleteValue(@PathVariable Long id) {
        return this.attributeService.deleteValue(id);
    }
}
