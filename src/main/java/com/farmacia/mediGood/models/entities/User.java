package com.farmacia.mediGood.models.entities;

import com.farmacia.mediGood.models.enums.Rol;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table()
public class User {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @Id
    private String email;
    @Getter
    @Setter

    private String password;
    @Getter
    @Setter

    private int phoneNumber;
    @Getter
    @Setter

    private String address;
    @Getter
    @Setter
    private Rol rol;

    public User(){

    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.rol= Rol.User;
    }





}
