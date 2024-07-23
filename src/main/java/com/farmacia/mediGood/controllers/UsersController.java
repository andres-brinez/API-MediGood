package com.farmacia.mediGood.controllers;

import com.farmacia.mediGood.models.DTOS.input.Users.CreateUserDTO;
import com.farmacia.mediGood.models.DTOS.input.Users.UserInformationDTO;
import com.farmacia.mediGood.models.DTOS.shared.ErrorResponseDTO;
import com.farmacia.mediGood.models.DTOS.shared.UserInformationProfileDTO;
import com.farmacia.mediGood.models.entities.User;
import com.farmacia.mediGood.services.UserService;
import com.farmacia.mediGood.services.UsersService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    private final UsersService userService;
    private final InformationValidator informationValidator;
    public UsersController(UsersService userService, InformationValidator informationValidator) {
        this.userService = userService;
        this.informationValidator = informationValidator;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO payload, BindingResult bindingResult) {

        ResponseEntity<?> responseValidation = informationValidator.validateInformation(bindingResult);


        if (responseValidation != null) {
            return responseValidation;
        }

        User user = userService.createUser(payload);

        System.out.println(user.toString());
        if (user == null) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO("User already exists."));
        }

        return ResponseEntity.ok(user);

    }

    // get user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO("User not found."));
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {

        List<User> users = userService.getAllUsers();

        if (users.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO("No users found."));
        }

        return ResponseEntity.ok(users);
    }

    // update user information
    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserInformationDTO payload, BindingResult bindingResult) {
        ResponseEntity<?> responseValidation = informationValidator.validateInformation(bindingResult);
        if (responseValidation != null) {
            return responseValidation;
        }
        User user = userService.updateUser(payload);
        if (user == null) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO("User not found."));
        }
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/hide/{email}")
    public ResponseEntity<?> hideUser(@PathVariable String email) {
        User user = userService.hideUser(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO("User not found."));
        }
        return ResponseEntity.ok(user);
    }
}
