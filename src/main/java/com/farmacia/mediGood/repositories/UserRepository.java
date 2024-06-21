package com.farmacia.mediGood.repositories;

import com.farmacia.mediGood.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
