package com.example.demo.repositories;

import com.example.demo.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends CrudRepository<Service, Long> {

    @Query("SELECT s FROM Service s WHERE s.name LIKE %?1%")
    List<Service> search(String keyword);

    @Query("SELECT COUNT(*) FROM Service")
    int numService();
}
