package com.farmacia.mediGood.controllers;

import com.farmacia.mediGood.models.DTOS.input.autentication.UserLoginDTO;
import com.farmacia.mediGood.models.DTOS.input.autentication.UserRegisterDTO;
import com.farmacia.mediGood.models.DTOS.output.UserToken;
import com.farmacia.mediGood.models.DTOS.shared.ErrorResponseDTO;
import com.farmacia.mediGood.models.entities.User;
import com.farmacia.mediGood.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController()
@RequestMapping("/api/v1/auth") // Corrección aquí

public class AuthController {

    private  final AuthService authService;
    private final  InformationValidator informationValidator;

    public AuthController(AuthService authService, InformationValidator informationValidator) {
        this.authService = authService;
        this.informationValidator = informationValidator;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDTO payload, BindingResult bindingResult) {
        
        // Validación del objeto recibido
        ResponseEntity<?> responseValidation = informationValidator.validateInformation(bindingResult);

        if (responseValidation != null) {
            return responseValidation;
        }

        User userRegister = authService.registerUser(payload);
        if (userRegister == null) {
            return ResponseEntity.badRequest().body("El correo electrónico ya existe.");
        }
        return ResponseEntity.ok(userRegister);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO payload, BindingResult bindingResult) {

        // Validación del objeto recibido
        ResponseEntity<?> responseValidation = informationValidator.validateInformation(bindingResult);

        if (responseValidation != null) {
            return responseValidation;
        }

        try{
            String token = authService.login(payload);
            UserToken userToken = new UserToken(token);
            return ResponseEntity.ok(userToken);
        }
        catch (BadCredentialsException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(e.getMessage()));
        }

    }


}