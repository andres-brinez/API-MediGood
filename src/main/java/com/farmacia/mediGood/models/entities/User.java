package com.farmacia.mediGood.models.entities;

import com.farmacia.mediGood.models.enums.Rol;
import jakarta.persistence.*;
import lombok.*;

@Entity
@ToString
@Data
public class User {

    @Id
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    private int phoneNumber;

    private String address;

    @Getter
    @Setter
    private Rol rol;

    public User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.rol = Rol.User;
    }
}