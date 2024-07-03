package com.farmacia.mediGood.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ToString
@Entity
@Data
@NoArgsConstructor
@Table(name = "purchase_product")
public class PurchaseProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private BigDecimal price;

    @Column(nullable = true)
    private BigDecimal total;

    @Column(nullable = true)
    private int quantity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    public PurchaseProduct( Purchase purchase,Product product, int quantity) {
        this.purchase = purchase;
        this.product = product;
        this.price = product.getPrice();
        this.quantity = quantity;
        this.total = new BigDecimal(this.quantity).multiply(this.getPrice());

    }
}
