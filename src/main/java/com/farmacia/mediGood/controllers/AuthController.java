package com.farmacia.mediGood.controllers;

import com.farmacia.mediGood.models.DTOS.input.autentication.UserLoginDTO;
import com.farmacia.mediGood.models.DTOS.input.autentication.UserRegisterDTO;
import com.farmacia.mediGood.models.DTOS.output.UserToken;
import com.farmacia.mediGood.models.entities.User;
import com.farmacia.mediGood.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/api/v1/auth") // Corrección aquí

public class AuthController {

    private  final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDTO payload, BindingResult bindingResult) {

        // Validación del objeto recibido
        ResponseEntity<?> responseValidation = validateInformation(bindingResult);

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
        ResponseEntity<?> responseValidation = validateInformation(bindingResult);

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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Credenciales incorrectas");
        }

    }

    public ResponseEntity<?> validateInformation(BindingResult bindingResult) {

        // Validación del objeto recibido
        List<String> errors = new ArrayList<>();

        if(bindingResult.hasErrors()){
            for(FieldError error : bindingResult.getFieldErrors()){
                errors.add(error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        return null;
    }
}