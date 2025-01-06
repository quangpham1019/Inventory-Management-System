package com.example.demo.Service.ServiceImpl;

import com.example.demo.Service.CommonService;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public class CommonServiceWithCRUD<T, TId> implements CommonService<T, TId> {

    private final CrudRepository<T, TId> repository;
    public CommonServiceWithCRUD(CrudRepository<T, TId> repository) {
        this.repository = repository;
    }

    @Override
    public List<T> findAll() {
        return (List<T>) repository.findAll();
    }

    @Override
    public T findById(TId id) {
        Optional<T> queryResult = repository.findById((TId) id);
        return queryResult.orElseThrow(() ->
                new IllegalArgumentException("Could not find object with id: " + id));
    }

    @Override
    public void deleteById(TId id) {
        repository.deleteById((TId) id);
    }

    @Override
    public void save(T object)  {
        repository.save(object);
    }

    @Override
    public void saveAll(List<T> objectList)  {
        repository.saveAll(objectList);
    }
}
