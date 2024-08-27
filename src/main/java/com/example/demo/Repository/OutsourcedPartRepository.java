package com.example.demo.Repository;

import com.example.demo.Domain.OutsourcedPart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 *
 *
 *
 */

@Repository
public interface OutsourcedPartRepository extends CrudRepository<OutsourcedPart,Long> {
}
