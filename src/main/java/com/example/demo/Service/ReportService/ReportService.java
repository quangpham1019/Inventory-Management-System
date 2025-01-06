package com.example.demo.Service.ReportService;

import com.example.demo.Domain.Report;

import java.util.List;

public interface ReportService {
    void save(Report report);
    List<Report> findAll();
    List<Report> findAllBy(String filterCriteria, String keyword);
}
