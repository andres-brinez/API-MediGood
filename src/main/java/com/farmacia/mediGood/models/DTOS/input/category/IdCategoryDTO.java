package com.farmacia.mediGood.models.DTOS.input.category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdCategoryDTO {


    @NotNull(message = "El id no puede estar en blanco")
    @Positive
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Long id;
}
