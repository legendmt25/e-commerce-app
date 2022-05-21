package com.example.test_api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectAttributesRequest {
    private Long attributeId1;
    private Long attributeId2;
}
