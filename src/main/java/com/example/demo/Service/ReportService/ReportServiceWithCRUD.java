package com.example.demo.Service.ReportService;

import com.example.demo.Domain.Report;
import com.example.demo.Repository.ReportRepository;
import com.example.demo.Service.CommonService.CommonServiceWithCRUD;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceWithCRUD extends CommonServiceWithCRUD<Report, Long> implements ReportService {
    private final ReportRepository reportRepository;

    public ReportServiceWithCRUD(ReportRepository reportRepository) {
        super(reportRepository);
        this.reportRepository = reportRepository;
    }

    @Override
    public List<Report> findAllBy(String filterCriteria, String reportKeyword) {
        if (filterCriteria == null || filterCriteria.isEmpty()) {filterCriteria = "";}
        return switch (filterCriteria) {
            case "user" -> reportRepository.findAllByUsername(reportKeyword);
            case "customer" -> reportRepository.findAllByCustomer_LastName(reportKeyword);
            default -> reportRepository.findAll();
        };
    }
}
