package com.example.test_api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
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
    @OneToMany(mappedBy = "attribute", cascade = CascadeType.ALL)
    private List<AttributeValue> values;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    public Attribute(String title, Product product) {
        this.title = title;
        this.values = new ArrayList<>();
        this.product = product;
    }
}
