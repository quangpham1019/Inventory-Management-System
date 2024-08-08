package com.example.demo.service;

import com.example.demo.domain.Customer;
import com.example.demo.domain.Report;
import com.example.demo.domain.User;
import com.example.demo.repositories.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService{
    private ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public List<Report> findAll() {
        return (List<Report>) reportRepository.findAll();
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
