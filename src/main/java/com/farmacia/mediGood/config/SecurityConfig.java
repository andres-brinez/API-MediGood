package com.farmacia.mediGood.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Permite acceder a la configuración de la aeguridad web con Spring Security
public class SecurityConfig {

    //Cadena de filtros de seguridad que tiene spring security para modificarla según la necesidad

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationProvider authenticationProvider,JwtAuthenticationFilter jwtAuthenticationFilter)
        throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                // configuraciones de autorización de las rutas
                .authorizeHttpRequests(request ->
                        request
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/farmacia/**").authenticated()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // politica de manejo de sesiones que se encarga de manejar las sesiones para que cada petición sea unica por lo que es Stateless que es una sesión no persistente
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }
}
