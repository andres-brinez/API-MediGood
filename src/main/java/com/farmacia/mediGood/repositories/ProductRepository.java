package com.farmacia.mediGood.repositories;

import com.farmacia.mediGood.models.entities.Category;
import com.farmacia.mediGood.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByInStock(boolean inStock);

    List<Product> findByCategory(Category category);

    List<Product> findByNameIgnoreCase(String categories);
    List<Product> findByNameContainingIgnoreCase(String name);

    // obtener los ultimos productos agregados
    List<Product> findTop10ByOrderByDateAddedAsc();
}
