package com.example.demo.Service.Data.Implementation;

import com.example.demo.Domain.Report;
import com.example.demo.Repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl
        extends CommonServiceImpl<Report, Long>
        implements com.example.demo.Service.Data.ReportService {

    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        super(reportRepository);
        this.reportRepository = reportRepository;
    }

    @Override
    public List<Report> findAllBy(String filterCriteria, String reportKeyword) {
        if (filterCriteria == null) filterCriteria = "";
        return switch (filterCriteria) {
            case "user" -> reportRepository.findAllByUsername(reportKeyword);
            case "customer" -> reportRepository.findAllByCustomer_LastName(reportKeyword);
            default -> reportRepository.findAll();
        };
    }
}
