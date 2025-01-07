package com.example.demo.Service.Data.Interface;

import com.example.demo.Domain.JcsServicing;

import java.util.List;

public interface JcsServicingService {

    JcsServicing findById(int serviceId);
    List<JcsServicing> listAllByKeyword(String keyword);
    void save(JcsServicing jcsServicing);
    void saveAll(List<JcsServicing> jcsServicingList);
    void deleteById(int serviceId);
    boolean hasService();
}
