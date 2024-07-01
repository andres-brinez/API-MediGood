package com.farmacia.mediGood.controllers;

import com.farmacia.mediGood.models.DTOS.shared.ErrorResponseDTO;
import com.farmacia.mediGood.models.DTOS.shared.SuccessResponseDTO;
import com.farmacia.mediGood.models.DTOS.shared.UserInformationProfileDTO;
import com.farmacia.mediGood.models.entities.User;
import com.farmacia.mediGood.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ap1/v1/user")
public class UserController {

    private final UserService userService;
    private final InformationValidator informationValidator;

    public UserController(UserService userService, InformationValidator informationValidator) {
        this.userService = userService;
        this.informationValidator = informationValidator;
    }

    @PutMapping("/updateProfile")
    public ResponseEntity<?> UpdateUser(@Valid @RequestBody UserInformationProfileDTO user, BindingResult bindingResult) {

       ResponseEntity<?> responseValidation = informationValidator.validateInformation(bindingResult);

       if (responseValidation != null) {
           return responseValidation;
       }

      try {
          User updateUser = userService.updateUser(user);

          if (updateUser == null) {
              return ResponseEntity
                      .status(HttpStatus.NOT_FOUND)
                      .body(new ErrorResponseDTO("Usuario no encontrado"));          }

          return ResponseEntity.ok(new SuccessResponseDTO("Usuario actualizado correctamente"));
      }
      catch (Exception e) {
          System.out.println(e.getMessage());
          return ResponseEntity.badRequest().body(new ErrorResponseDTO("Error al actualizar el usuario"));
      }
    }

}
