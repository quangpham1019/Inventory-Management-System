package com.example.demo.Service.Implementation.UsingCRUDRepository;

import com.example.demo.Domain.Report;
import com.example.demo.Repository.CRUDRepository.ReportRepository;
import com.example.demo.Service.Interface.ReportService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceUsingCRUDRepository
        extends CommonServiceUsingCRUDRepository<Report, Long>
        implements ReportService {

    private final ReportRepository reportRepository;

    public ReportServiceUsingCRUDRepository(ReportRepository reportRepository) {
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
