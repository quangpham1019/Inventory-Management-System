package com.example.demo.service;

import com.example.demo.domain.Report;

import java.util.List;

public interface ReportService {
    void save(Report report);
    List<Report> findAll();
}
