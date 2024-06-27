package com.farmacia.mediGood.servicies;

import com.farmacia.mediGood.models.DTOS.UserRegisterDTO;
import com.farmacia.mediGood.models.entities.User;
import com.farmacia.mediGood.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
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
}



