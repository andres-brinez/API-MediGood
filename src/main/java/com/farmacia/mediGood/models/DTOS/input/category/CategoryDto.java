package com.farmacia.mediGood.models.DTOS.input.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    @NotBlank(message = "El nombre no puede estar vacío")
    @NotNull(message = "El nombre no puede estar en blanco")
    private String name;
}
