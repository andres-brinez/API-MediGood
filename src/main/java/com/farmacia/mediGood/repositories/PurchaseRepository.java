package com.farmacia.mediGood.repositories;

import com.farmacia.mediGood.models.entities.Purchase;
import com.farmacia.mediGood.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {



    List<Purchase>  findAllByUser(User user);
}
