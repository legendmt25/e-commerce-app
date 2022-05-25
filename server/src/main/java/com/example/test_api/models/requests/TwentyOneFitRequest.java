package com.example.test_api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TwentyOneFitRequest {
    private String name;
    private String weight;
    private String phoneNumber;
    private String purpose;
    private Date dateTime;
}
