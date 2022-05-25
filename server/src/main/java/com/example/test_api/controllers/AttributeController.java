package com.example.test_api.controllers;

import com.example.test_api.models.AttributeValue;
import com.example.test_api.models.input.AttributeValueInput;
import com.example.test_api.models.requests.ConnectAttributeRequest;
import com.example.test_api.models.requests.ConnectAttributesRequest;
import com.example.test_api.services.AttributeService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/v1/attributes")
@RestController
@CrossOrigin("*")
public class AttributeController {

    private final AttributeService attributeService;

    public AttributeController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @PostMapping("/connect-attribute")
    public Boolean connectAttribute(@RequestBody ConnectAttributeRequest body) {
        return this.attributeService.connectAttributes(
                body.getSource(),
                body.getTarget()
        );
    }

    @PostMapping("/connect-attributes")
    public List<ConnectAttributeRequest> connectAttributes(@RequestBody ConnectAttributesRequest body) {
        var successful = new ArrayList<ConnectAttributeRequest>();
        for (var connection : body.getConnections()) {
            if (this.attributeService.connectAttributes(connection.getSource(), connection.getTarget())) {
                successful.add(connection);
            }
        }
        return successful;
    }

    @PostMapping("/disconnect-attribute")
    public Boolean disconnectAttribute(@RequestBody ConnectAttributeRequest body) {
        return this.attributeService.disconnectAttributes(
                body.getSource(),
                body.getTarget()
        );
    }

    @PostMapping("/disconnect-attributes")
    public List<ConnectAttributeRequest> disconnectAttributes(@RequestBody ConnectAttributesRequest body) {
        var successful = new ArrayList<ConnectAttributeRequest>();
        for (var connection : body.getConnections()) {
            if (this.attributeService.disconnectAttributes(connection.getSource(), connection.getTarget())) {
                successful.add(connection);
            }
        }
        return successful;
    }

    @PostMapping("/{id}/add-value")
    public AttributeValue addValue(@PathVariable Long id, @RequestBody AttributeValueInput input) {
        return this.attributeService.addValue(id, input);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteAttribute(@PathVariable Long id) {
        return this.attributeService.deleteById(id);
    }
}
