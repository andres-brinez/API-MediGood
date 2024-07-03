package com.farmacia.mediGood.models.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "sales")
@Data
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date;

    private BigDecimal total;

    private int quantity=0;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)

    private List<PurchaseProduct> products;

    public Purchase() {
    }

    public Purchase(User user) {
        this.date = LocalDateTime.now();
        this.user = user;

    }

    public void calculatePurchaseInformation() {
        if (products != null && !products.isEmpty()) {
            this.quantity = products.stream().mapToInt(PurchaseProduct::getQuantity).sum();
            this.total = products.stream()
                    .map(pp -> pp.getPrice().multiply(BigDecimal.valueOf(pp.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }
}