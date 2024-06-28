package com.farmacia.mediGood.controllers;

import com.farmacia.mediGood.models.DTOS.UserLoginDTO;
import com.farmacia.mediGood.models.DTOS.UserRegisterDTO;
import com.farmacia.mediGood.models.DTOS.output.UserToken;
import com.farmacia.mediGood.models.entities.User;
import com.farmacia.mediGood.servicies.UserService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private  final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDTO payload, BindingResult bindingResult) {



        User userRegister = userService.registerUser(payload);
        if (userRegister == null) {
            return ResponseEntity.badRequest().body("El correo electrónico ya existe.");
        }
        return ResponseEntity.ok(userRegister);


    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO payload, BindingResult bindingResult) {

        // Validación del objeto recibido
        if(bindingResult.hasErrors()){
            List<String> errors = new ArrayList<>();
            for(FieldError error : bindingResult.getFieldErrors()){
                errors.add(error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        try{
            String token = userService.login(payload);
            UserToken userToken = new UserToken(token);
            return ResponseEntity.ok(userToken);
        }
        catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }

    }

    // TODO: Terminar esta función de validar la información
    public ResponseEntity<?> validateInformation(BindingResult bindingResult) {

        // Validación del objeto recibido
        List<String> errors = new ArrayList<>();

        if(bindingResult.hasErrors()){
            for(FieldError error : bindingResult.getFieldErrors()){
                errors.add(error.getDefaultMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}