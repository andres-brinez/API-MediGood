package com.farmacia.mediGood.config;

import com.farmacia.mediGood.repositories.AuthRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



// Esta clase es un archivo de configuración de Spring Boot. Puede contener cualquier código que se requiera para configurar la aplicación.
@Configuration // Indica que es una clase de configuración que es la fuente de definiciones de beans para el contenedor de Spring
// Esta clase pyede contener metodos anotados con @Bean, que se utilizaran para definir y configurar beans dentro de la aplicación de spring
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Gestionar los usuarios dentro de la aplicación
    @Bean
    public UserDetailsService userDetailsService(AuthRepository repository) {
        // Busca el usuario en la DB y devuelve un objeto UserDetails que representa al usuario y si no lo encuentra lanza una excepción
        return username -> repository.findByPassword(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    // Provedor de autenticación que se encarga de autenticar al usuario, valída las credenciales del usuario y autentíca su identidad
    // Implementa las estrategías de autenticación de Spring
    @Bean
    public AuthenticationProvider authenticationProvider(
            AuthRepository repository,
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService(repository));
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    // Es el que se encarga de manejar la autenticación de los usuarios
    @Bean
    public AuthenticationManager authenticationManager(
      AuthenticationConfiguration provider  ) throws Exception {
        return provider.getAuthenticationManager();
    }
}
