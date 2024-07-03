package com.farmacia.mediGood.models.DTOS.input.Sale;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
public class ProductPurchaseDTO {

    @NotNull(message = "El id del producto es obligatoria")
    private Long id;
    @NotNull(message = "La cantidad del producto es obligatoria")
    @Size(min = 1, message = "La cantidad minima del producto a compras es 1 ")
    private int quantity;
}
