package com.example.demo.repositories;

import com.example.demo.domain.ItemTangible;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemTangibleRepository extends CrudRepository<ItemTangible, Long> {
}
