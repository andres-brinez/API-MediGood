package com.farmacia.mediGood.models.DTOS.input.Sale;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {

    @NotNull(message = "El email del usuario no puede estar vacío")
    @Email(message = "El email del usuario debe tener un formato válido")
    private String emailUser;
    @NotNull(message = "Para realizar la compra debe haber productos")
    private List<ProductPurchaseDTO> products;

}
