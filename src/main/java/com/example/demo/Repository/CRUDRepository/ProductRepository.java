package com.example.demo.Repository.CRUDRepository;

import com.example.demo.Domain.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 *
 *
 *
 */

@Repository
public interface ProductRepository extends CrudRepository<Product,Long> {
    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
    List<Product> search(String keyword);
}
