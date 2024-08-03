package com.farmacia.mediGood;

import com.farmacia.mediGood.models.DTOS.input.autentication.UserLoginDTO;
import com.farmacia.mediGood.models.DTOS.input.autentication.UserRegisterDTO;
import com.farmacia.mediGood.models.entities.User;
import com.farmacia.mediGood.repositories.AuthRepository;
import com.farmacia.mediGood.services.AuthService;
import com.farmacia.mediGood.services.JwtService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthServiceTest {

    @Mock
    private AuthRepository authRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    public void testRegisterUser() {
        UserRegisterDTO userDto = new UserRegisterDTO("John Doe", "johnjj.doe@example.com", "password123");
        User newUser = new User(userDto.getName(), userDto.getEmail(), userDto.getPassword());

        Mockito.when(authRepository.findById(newUser.getEmail())).thenReturn(Optional.empty());
        Mockito.when(authRepository.save(newUser)).thenReturn(newUser);

        User result = authService.registerUser(userDto);
        System.out.println(result);

        assertNull(result);

    }

    @Test
    public void testLogin() {
        UserLoginDTO userLoginDTO = new UserLoginDTO("john.doe@example.com", "password123");
        User user = new User("John Doe", "john.doe@example.com", "encryptedPassword");

        Mockito.when(authRepository.findById(userLoginDTO.getEmail())).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())).thenReturn(true);
        Mockito.when(jwtService.generateToken(user)).thenReturn("mockedToken");

        String result = authService.login(userLoginDTO);
        System.out.println(result);

        assertNotNull(result);
        assertEquals("mockedToken", result);
    }
}
