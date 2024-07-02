package com.farmacia.mediGood.models.DTOS.input.Users;

import com.farmacia.mediGood.models.enums.Rol;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInformationDTO {
    @NotBlank(message = "El email no puede estar vacío")
    @NotBlank(message = "El email no puede estar en blanco")
    private String email;
    private String name;
    private int phoneNumber;
    private String address;
    private int age;
    private int dni;
    private Rol rol;
}
