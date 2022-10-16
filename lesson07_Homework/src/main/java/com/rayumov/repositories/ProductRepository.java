package com.rayumov.repositories;

import com.rayumov.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCostBetween(Integer min, Integer max);

    List<Product> findAllByCostAfter(Integer min);

    List<Product> findAllByCostBefore(Integer max);

}
