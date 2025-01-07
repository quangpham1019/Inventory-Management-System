package com.example.demo.Service.Data.Interface;

import com.example.demo.Domain.Report;

import java.util.List;

public interface ReportService extends CommonService<Report, Long> {
    List<Report> findAllBy(String filterCriteria, String keyword);
}
