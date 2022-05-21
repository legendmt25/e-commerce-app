package com.example.test_api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Table(name = "attribute_value")
@Entity
@NoArgsConstructor
@Data
public class AttributeValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;

    @JsonIgnoreProperties(value = "attributes")
    @ManyToMany
    @Fetch(FetchMode.SELECT)
    private List<AttributeValue> attributes;

    public AttributeValue(String value) {
        this.value = value;
    }
}
