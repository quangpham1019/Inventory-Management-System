package com.example.demo.service;

import com.example.demo.domain.Report;
import com.example.demo.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService{
    private ReportRepository reportRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public List<Report> findAll() {
        return (List<Report>) reportRepository.findAll();
    }
    @Override
    public void save(Report report) {
        reportRepository.save(report);
    }

}
