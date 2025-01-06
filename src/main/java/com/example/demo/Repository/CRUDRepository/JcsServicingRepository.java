package com.example.demo.Repository.CRUDRepository;

import com.example.demo.Domain.JcsServicing;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JcsServicingRepository extends CrudRepository<JcsServicing, Long> {

    @Query("SELECT s FROM JcsServicing s WHERE s.name LIKE %?1%")
    List<JcsServicing> search(String keyword);

    @Query("SELECT COUNT(*) FROM JcsServicing")
    int numService();
}
