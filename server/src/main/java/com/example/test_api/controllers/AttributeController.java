package com.example.test_api.controllers;

import com.example.test_api.models.requests.ConnectAttributesRequest;
import com.example.test_api.services.AttributeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/attributes")
@RestController
public class AttributeController {

    private final AttributeService attributeService;

    public AttributeController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @PostMapping("/connect-attributes")
    public Boolean connectAttributes(@RequestBody ConnectAttributesRequest body) {
        return this.attributeService.connectAttributes(
                body.getAttributeId1(),
                body.getAttributeId2()
        );
    }
}
