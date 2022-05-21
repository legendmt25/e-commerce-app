package com.example.test_api.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Table(name = "attribute")
@Entity
@Data
@NoArgsConstructor
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @OneToMany
    private List<AttributeValue> values;

    public Attribute(String title, List<AttributeValue> values) {
        this.title = title;
        this.values = values;
    }
}
