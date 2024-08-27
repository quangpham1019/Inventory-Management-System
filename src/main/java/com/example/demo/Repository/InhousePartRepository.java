package com.example.demo.Repository;

import com.example.demo.Domain.InhousePart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 *
 *
 *
 */
@Repository
public interface InhousePartRepository extends CrudRepository<InhousePart,Long> {
}
