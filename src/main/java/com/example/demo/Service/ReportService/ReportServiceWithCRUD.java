package com.example.demo.Service.ReportService;

import com.example.demo.Domain.Report;
import com.example.demo.Repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceWithCRUD implements ReportService {
    private ReportRepository reportRepository;

    public ReportServiceWithCRUD(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    @Override
    public List<Report> findAllBy(String filterCriteria, String reportKeyword) {
        switch (filterCriteria){
            case "user":
                return reportRepository.findAllByUsername(reportKeyword);
            case "customer":
                return reportRepository.findAllByCustomer_LastName(reportKeyword);
        }
        return reportRepository.findAll();
    }

    @Override
    public void save(Report report) {
        reportRepository.save(report);
    }


}
