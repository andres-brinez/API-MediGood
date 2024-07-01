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

    /**
     * Actualiza la información de un usuario en la base de datos.
     *
     * @param payload La información del usuario a actualizar.
     * @return El objeto de usuario actualizado, o null si el usuario no existe.
     */
    public User updateUser(UserInformationProfileDTO payload) {
        // Busca el usuario en la base de datos por su correo electrónico
        Optional<User> optionalUser = userRepository.findById(payload.getEmail());

        // Si el usuario no existe, devuelve null
        if (optionalUser.isEmpty()) {
            return null;
        }

        User userDB = optionalUser.get();

        // Recorre todos los campos de la clase UserDB
        for (Field userDbField : userDB.getClass().getDeclaredFields()) {
            userDbField.setAccessible(true);

            // Recorre todos los campos de la clase UserDto
            for (Field UserDtoField : payload.getClass().getDeclaredFields()) {
                UserDtoField.setAccessible(true);

                // Verifica si son el mismo campo
                if(userDbField.getName().equals(UserDtoField.getName())) {

                    // Válida y actualiza el objeto de usuario
                    validateObject(userDB, payload, userDbField, UserDtoField);
                }
            }
        }

        // Guarda el objeto de usuario actualizado en la base de datos y lo devuelve
        return userRepository.save(userDB);
    }

    /**
     * Válida y actualiza un objeto de usuario basado en los campos proporcionados.
     *
     * @param userDB El objeto de usuario opcional de la base de datos.
     * @param user La información del usuario a actualizar.
     * @param fieldUserDb El campo del objeto de usuario de la base de datos.
     * @param fieldUser El campo de la información del usuario a actualizar.
     */
    private void validateObject(User userDB, UserInformationProfileDTO user, Field fieldUserDb, Field fieldUser) {
        try {
            // Si el campo no está vacío
            if (fieldUser.get(user) != null) {
                // Si el campo no es igual a 0
                if (!fieldUser.get(user).equals(0)){
                    // Actualiza el campo del objeto de usuario de la base de datos
                    fieldUserDb.set(userDB, fieldUser.get(user));
                }
            }
        } catch (IllegalAccessException e) {
            // Maneja la excepción
            e.printStackTrace();
        }
    }
}

