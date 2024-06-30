package com.farmacia.mediGood.repositories;

import com.farmacia.mediGood.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User,String> {

    Optional<User> findByPassword(String password);
}
