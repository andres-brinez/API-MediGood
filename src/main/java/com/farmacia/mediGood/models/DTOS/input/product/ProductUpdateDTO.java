package com.farmacia.mediGood.models.DTOS.input.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductUpdateDTO
{
    @NotNull(message = "El id no puede estar vacío")
    @Positive(message = "El id no puede ser negativo")
    private Long id;

    private String name=null;
    private BigDecimal price=null;
    private String description=null;
    private Long categoryId=null;
    private Boolean inStock=null;
    private Integer quantity=null;
}
