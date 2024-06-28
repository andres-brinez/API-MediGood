package com.farmacia.mediGood.servicies;

import com.farmacia.mediGood.models.DTOS.UserLoginDTO;
import com.farmacia.mediGood.models.DTOS.UserRegisterDTO;
import com.farmacia.mediGood.models.entities.User;
import com.farmacia.mediGood.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private  final UserRepository userRepository;

    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public User registerUser(UserRegisterDTO user){

        User newUser = new User(user.getName(),user.getEmail(),user.getPassword());
        newUser.passwordEncoder();

        Optional<User> optionalUsuario = userRepository.findById(newUser.getEmail());

        if (optionalUsuario.isPresent()) {
            return null;
        }
        return userRepository.save(newUser);
    }

    public String login(UserLoginDTO userInformation) {

        Optional<User> user = userRepository.findById(userInformation.getEmail());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (user.isEmpty()|| !encoder.matches(userInformation.getPassword(), user.get().getPassword())) {
            throw new BadCredentialsException("Usuario o contraseña incorrectos");
        }
        return generateToken(user.get());
    }

    private  String generateToken( User user) {
        return "token";
    }


}



