package com.example.demo.Service.CommonService;

import java.util.List;

public interface CommonService<T, TId> {
    List<T> findAll();
    T findById(TId id);
    void deleteById(TId id);
    void save(T object);
    void saveAll(List<T> objectList);
}
