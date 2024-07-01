package com.farmacia.mediGood.services;

import com.farmacia.mediGood.models.DTOS.shared.UserInformationProfileDTO;
import com.farmacia.mediGood.models.entities.User;
import com.farmacia.mediGood.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Optional;

@Service
public class UserService {

    private  final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User UpdateUser(UserInformationProfileDTO user) {

        Optional<User> userDB = userRepository.findById(user.getEmail());
        if (userDB.isEmpty()) {
            return null;
        }

        // Reccorre todos los atributos de la clase UserDb
        for (Field fieldUserDb : userDB.get().getClass().getDeclaredFields()) {
            fieldUserDb.setAccessible(true);

            // Reccorre todos los atributos de la clase UserDto
            for (Field fieldUser : user.getClass().getDeclaredFields()) {
                fieldUser.setAccessible(true);

                // Valida que sea el mismo atributo
                if(fieldUserDb.getName().equals(fieldUser.getName())) {

                    try {

                        // si el objeto no se encuentra vacio
                        if (fieldUser.get(user) != null) {
                            if (!fieldUser.get(user).equals(0)){
                                fieldUserDb.set(userDB.get(), fieldUser.get(user));
                                break;
                            }
                        }
                    }
                    catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

        }


        return userRepository.save(userDB.get());
    }
}
