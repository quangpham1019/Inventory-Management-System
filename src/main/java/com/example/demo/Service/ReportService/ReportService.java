package com.example.demo.Service.ReportService;

import com.example.demo.Domain.Report;
import com.example.demo.Service.CommonService.CommonService;

import java.util.List;

public interface ReportService extends CommonService<Report, Long> {
    List<Report> findAllBy(String filterCriteria, String keyword);
}
