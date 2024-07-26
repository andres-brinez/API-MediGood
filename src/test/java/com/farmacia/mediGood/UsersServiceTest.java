package com.farmacia.mediGood;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.farmacia.mediGood.models.enums.Rol;
import com.farmacia.mediGood.services.UsersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.farmacia.mediGood.models.DTOS.input.Users.CreateUserDTO;
import com.farmacia.mediGood.models.entities.User;
import com.farmacia.mediGood.repositories.UserRepository;

import javax.management.relation.Role;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UsersService usersService;

    @Test
    public void testCreateUser() {
        CreateUserDTO createUserDTO = new CreateUserDTO("John Doe", "john.doe@example.com", "password", Rol.USER);

        User newUser = new User(createUserDTO.getName(), createUserDTO.getEmail(), createUserDTO.getPassword(), createUserDTO.getRol());

        when(userRepository.findById("john.doe@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(Mockito.any(User.class))).thenReturn(newUser); // Stubbing con cualquier objeto User

        User result = usersService.createUser(createUserDTO);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals(Rol.USER, result.getRol());
    }

    @Test
    public void testGetUserByEmail() {
        String userEmail = "john.doe@example.com";
        User user = new User("John Doe", userEmail, "password", Rol.USER);

        when(userRepository.findById(userEmail)).thenReturn(Optional.of(user));

        User result = usersService.getUserByEmail(userEmail);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john.doe@example.com", result.getEmail());
    }

    @Test
    public void testHideUser() {
        String userEmail = "john.doe@example.com";
        User user = new User("John Doe", userEmail, "password", Rol.USER);

        when(userRepository.findById(userEmail)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User result = usersService.hideUser(userEmail);

        assertNotNull(result);
        assertFalse(result.isEnabled());
    }

    // Add more test cases for other methods in the UsersService class as needed
}