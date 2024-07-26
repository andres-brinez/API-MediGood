package com.farmacia.mediGood;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.farmacia.mediGood.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.farmacia.mediGood.models.DTOS.shared.UserInformationProfileDTO;
import com.farmacia.mediGood.models.entities.User;
import com.farmacia.mediGood.repositories.UserRepository;
import java.util.Optional;
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testUpdateUser() {
        UserInformationProfileDTO userDto = new UserInformationProfileDTO();
        userDto.setEmail("test@example.com");
        userDto.setName("John");

        User user = new User();
        user.setEmail("test@example.com");
        user.setName("Jane");

        when(userRepository.findById("test@example.com")).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.updateUser(userDto);

        assertNotNull(result);
        assertEquals("John", result.getName());
    }

    // Add more test cases for other methods in the UserService class as needed
}