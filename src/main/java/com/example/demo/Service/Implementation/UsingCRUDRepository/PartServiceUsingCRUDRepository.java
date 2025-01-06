package com.example.demo.Service.Implementation.UsingCRUDRepository;

import com.example.demo.Domain.Part;
import com.example.demo.Domain.Report;
import com.example.demo.Repository.CRUDRepository.PartRepository;
import com.example.demo.Repository.CRUDRepository.ReportRepository;
import com.example.demo.Service.Interface.PartService;
import com.example.demo.Service.Interface.ReportService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */

@Service
public class PartServiceUsingCRUDRepository extends CommonServiceUsingCRUDRepository<Part, Long> implements PartService {
    private PartRepository partRepository;

    public PartServiceUsingCRUDRepository(PartRepository partRepository) {
        super(partRepository);
        this.partRepository = partRepository;
    }

    @Override
    public List<Part> listAllByKeyword(String keyword) {
        if (keyword != null) {
            return partRepository.search(keyword);
        }
        return (List<Part>) partRepository.findAll();
    }

    @Override
    public boolean partInUse(long id) {
        Part part = findById(id);
        return !part.getProducts().isEmpty();
    }

    @Service
    public static class ReportServiceUsingCRUDRepository extends CommonServiceUsingCRUDRepository<Report, Long> implements ReportService {
        private final ReportRepository reportRepository;

        public ReportServiceUsingCRUDRepository(ReportRepository reportRepository) {
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
}
