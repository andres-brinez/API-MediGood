package com.farmacia.mediGood.servicies;

import com.farmacia.mediGood.models.DTOS.UserRegisterDTO;
import com.farmacia.mediGood.models.entities.User;
import com.farmacia.mediGood.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private  final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void  saveUser(UserRegisterDTO user){
        userRepository.save(new User(user.getName(),user.getEmail(),user.getPassword()));
    }
}
