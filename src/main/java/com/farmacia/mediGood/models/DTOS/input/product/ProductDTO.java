package com.farmacia.mediGood.models.DTOS.input.product;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDTO {

    @NotNull(message = "El id no puede estar vacío")
    @Positive(message = "El id no puede ser negativo")
    private Long id;

    @NotNull(message = "El nombre no puede estar vacío")
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @NotNull(message = "El precio no puede estar vacío")
    @PositiveOrZero(message = "El precio no puede ser negativo")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private BigDecimal price;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String description;

    @NotNull(message = "La imagen no puede estar vacia")
    private MultipartFile imageFile;

    @NotNull(message = "La categoría no puede estar vacia")
    @Positive(message = "La categoría no puede ser negativa")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Long categoryId;

    @NotNull(message = "La disponibilidad no puede estar vacía")
    private boolean inStock;

    @NotNull(message = "La cantidad no puede estar vacia")
    @Positive(message = "La cantidad no puede ser negativa")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private int quantity;
}