package com.example.security.part;

import com.example.security.product.Product;
import com.example.security.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Part {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Double price;
    private Integer stock;
    private Integer min;
    private Integer max;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
