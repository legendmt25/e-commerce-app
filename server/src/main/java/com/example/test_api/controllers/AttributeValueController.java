package com.example.test_api.controllers;

import com.example.test_api.models.requests.ConnectAttributeRequest;
import com.example.test_api.models.requests.ConnectAttributesRequest;
import com.example.test_api.services.AttributeService;
import com.example.test_api.services.AttributeValueService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RequestMapping("/api/v1/attribute-values")
@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class AttributeValueController {
    private final AttributeValueService attributeValueService;

    @DeleteMapping("/{id}")
    public Boolean deleteValue(@PathVariable Long id) {
        return this.attributeValueService.delete(id);
    }

    @PostMapping("/connect")
    public List<ConnectAttributeRequest> connectAttributes(@RequestBody ConnectAttributesRequest body) {
        var successful = new ArrayList<ConnectAttributeRequest>();
        for (var connection : body.getConnections()) {
            if (this.attributeValueService.connect(connection.getSource(), connection.getTarget())) {
                successful.add(connection);
            }
        }
        return successful;
    }

    @PostMapping("/disconnect")
    public List<ConnectAttributeRequest> disconnectAttributes(@RequestBody ConnectAttributesRequest body) {
        var successful = new ArrayList<ConnectAttributeRequest>();
        for (var connection : body.getConnections()) {
            if (this.attributeValueService.disconnect(connection.getSource(), connection.getTarget())) {
                successful.add(connection);
            }
        }
        return successful;
    }

    //    @PostMapping("/disconnect")
//    public Boolean disconnectAttribute(@RequestBody ConnectAttributeRequest body) {
//        return this.attributeValueService.disconnect(
//                body.getSource(),
//                body.getTarget()
//        );
//    }

//    @PostMapping("/connect")
//    public Boolean connectAttribute(@RequestBody ConnectAttributeRequest body) {
//        return this.attributeValueService.connect(
//                body.getSource(),
//                body.getTarget()
//        );
//    }
}
