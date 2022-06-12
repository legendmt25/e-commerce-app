package com.example.test_api.controllers;

import com.example.test_api.models.requests.TwentyOneFitRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/challenges")
@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class ChallengesController {
    @PostMapping("/21fit")
    public Boolean twentyOneFitChallenge(@RequestBody TwentyOneFitRequest body) {
        return true;
    }

}
