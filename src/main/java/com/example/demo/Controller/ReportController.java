package com.example.demo.Controller;

import com.example.demo.Domain.Report;
import com.example.demo.Service.Interface.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@PreAuthorize("hasAuthority('ADMIN')")
@Controller
@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public String getReportPage(Model model,
                                @Param("reportKeyword") String reportKeyword,
                                @Param("filterCriteria") String filterCriteria) {
        List<Report> reports = reportService.findAllBy(filterCriteria, reportKeyword);

        model.addAttribute("reportKeyword", reportKeyword);
        model.addAttribute("reports", reports);
        return "menu pages/reports";
    }
}
