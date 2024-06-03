package com.example.security.product;

import com.example.security.part.Part;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Double price;
    private Integer stock;
    private Integer min;
    private Integer max;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Part> parts;

}
