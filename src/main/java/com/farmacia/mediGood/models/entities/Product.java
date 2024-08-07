package com.farmacia.mediGood.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false,length = 1000)
    private String description;

    @Column(nullable = false)
    private String imageUrl;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private boolean inStock;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateAdded;

    public Product(Long id,
                   String name,
                   BigDecimal price,
                   String description,
                   String imageUrl,
                   Category category,
                   boolean inStock,
                   int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
        this.inStock = inStock;
        this.quantity = quantity;

    }



    @PrePersist
    public void prePersist() {
        this.dateAdded = LocalDateTime.now();
    }
}