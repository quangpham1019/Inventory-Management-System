package com.example.demo.Repository;

import com.example.demo.Domain.Service;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JcsServicingRepository extends CrudRepository<Service, Long> {

    @Query("SELECT s FROM Service s WHERE s.name LIKE %?1%")
    List<Service> search(String keyword);

    @Query("SELECT COUNT(*) FROM Service")
    int numService();
}
