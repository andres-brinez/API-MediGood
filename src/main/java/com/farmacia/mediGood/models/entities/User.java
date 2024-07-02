package com.farmacia.mediGood.models.entities;

import com.farmacia.mediGood.models.enums.Rol;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.List;

@ToString
@Data
@NoArgsConstructor
@Table(name = "users")
@Entity
// Para que spring boot reconozca la clase como una fuente de datos de usuario válida para la autenticación debe extender de UserDetail
// La interfaz UserDetails proporciona una representación básica de un usuario en Spring Security.
public class User implements UserDetails {

    @Id
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    private int phoneNumber;
    private String address;
    private int age;
    private int dni;
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private Rol rol;


    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.rol = Rol.USER;
        this.enabled = true;
    }


    public User(String name,String email, String password, Rol rol) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.enabled = true;
    }
    public void passwordEncoder() {
        this.password = new BCryptPasswordEncoder().encode(this.password);
    }


    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    // Este método obtiene los permisos que tienen el usuario
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol.name()));
    }

    /**
     * Returns the username used to authenticate the user. Cannot return
     * <code>null</code>.
     *
     * @return the username (never <code>null</code>)
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user's account is valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isCredentialsNonExpired() {

        return UserDetails.super.isCredentialsNonExpired();
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    @Override
    public boolean isEnabled() {
        //return UserDetails.super.isEnabled();
        return enabled;
    }
}


