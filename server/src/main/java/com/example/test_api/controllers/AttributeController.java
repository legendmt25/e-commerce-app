package com.example.test_api.controllers;

import com.example.test_api.models.AttributeValue;
import com.example.test_api.models.input.AttributeValueInput;
import com.example.test_api.models.requests.ConnectAttributeRequest;
import com.example.test_api.models.requests.ConnectAttributesRequest;
import com.example.test_api.services.AttributeService;
import com.example.test_api.services.AttributeValueService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/v1/attributes")
@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class AttributeController {
    private final AttributeService attributeService;

    @PostMapping("/{id}/add-value")
    public AttributeValue addValue(@PathVariable Long id, @RequestBody AttributeValueInput input) {
        return this.attributeService.addValue(id, input);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteAttribute(@PathVariable Long id) {
        return this.attributeService.deleteById(id);
    }
}
