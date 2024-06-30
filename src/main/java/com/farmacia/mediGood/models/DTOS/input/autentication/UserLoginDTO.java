package com.farmacia.mediGood.models.DTOS.input.autentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    @NotNull(message = "El email no puede estar vacío")
    @NotBlank(message = "El email no puede estar en blanco")
    @Email(message = "El email debe tener un formato válido")
    private String email;

    @NotNull(message = "La contraseña no puede estar vacía")
    @NotBlank(message = "La contraseña no puede estar en blanco")
    private String password;


}
