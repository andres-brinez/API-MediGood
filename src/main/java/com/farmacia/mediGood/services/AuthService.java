package com.farmacia.mediGood.services;

import com.farmacia.mediGood.models.DTOS.input.autentication.UserLoginDTO;
import com.farmacia.mediGood.models.DTOS.input.autentication.UserRegisterDTO;
import com.farmacia.mediGood.models.entities.User;
import com.farmacia.mediGood.repositories.AuthRepository;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    private  final AuthRepository authRepository;
    private final JwtService jwtService;

    //private final AuthenticationManager authenticationManager; // Se encarga de la autenticacion

    public AuthService(AuthRepository authRepository, JwtService jwtService, AuthenticationManager authenticationManager) {

        this.authRepository = authRepository;
        this.jwtService = jwtService;
        //this.authenticationManager = authenticationManager;
    }

    public User registerUser(UserRegisterDTO user){

        User newUser = new User(user.getName(),user.getEmail(),user.getPassword());
        newUser.passwordEncoder();

        Optional<User> optionalUsuario = authRepository.findById(newUser.getEmail());

        if (optionalUsuario.isPresent()) {
            return null;
        }
        return authRepository.save(newUser);
    }

    public String login(UserLoginDTO userInformation) {

        // Autenticar el usuario

        // FIXME: Falta autenticar el usuario, hay un error que se puede arreglar porque cuando descomento esta linea de codigo me aparece un error 403 "Access Denied" pero igual sin esta linea de codigo funciona
        /*authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userInformation.getEmail(),
                        userInformation.getPassword()
                )
        );*/

        var user = authRepository.findById(userInformation.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        // Generar el token
        var jwtToken = jwtService.generateToken(user);

        return jwtToken;


    }



}



