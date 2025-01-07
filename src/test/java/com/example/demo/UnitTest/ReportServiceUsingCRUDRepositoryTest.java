package com.example.demo.UnitTest;

import com.example.demo.Domain.Customer;
import com.example.demo.Domain.Report;
import com.example.demo.Repository.CRUDRepository.ReportRepository;
import com.example.demo.Service.Data.Implementation.UsingCRUDRepository.ReportServiceUsingCRUDRepository;
import com.example.demo.Service.Data.Interface.ReportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SpringBootTest
public class ReportServiceUsingCRUDRepositoryTest {

    @Test
    public void testFindAllBy() {
        // ARRANGE
        // Set up class to be tested
        ReportRepository reportRepository = Mockito.mock(ReportRepository.class);
        ReportService reportService = new ReportServiceUsingCRUDRepository(reportRepository);

        // Initialize data as variables
        String SMITH = "smith";
        String DOE = "doe";
        String JIM = "jim";
        String ADMIN = "admin";
        String USER = "user";
        String USER_CRITERIA = "user";
        String CUSTOMER_CRITERIA = "customer";

        List<Customer> customers = Arrays.asList(
                new Customer("", "", SMITH, "", "", "",""),
                new Customer("", "", DOE, "", "", "","")
        );

        Customer lastNameSmith = customers.get(0);
        Customer lastNameDoe = customers.get(1);

        // Generate mock data
        List<Report> reports = Arrays.asList(
                new Report(null, ADMIN, lastNameSmith, 100),
                new Report(null, USER, lastNameSmith, 200),
                new Report(null, ADMIN, lastNameDoe, 300),
                new Report(null, USER, lastNameDoe, 200),
                new Report(null, ADMIN, lastNameSmith, 500)
        );
        List<Report> reportsForAdminExpected =
                reports
                        .stream()
                        .filter(report -> Objects.equals(report.getUsername(), ADMIN))
                        .toList();
        List<Report> reportsForUserExpected =
                reports
                        .stream()
                        .filter(report -> Objects.equals(report.getUsername(), USER))
                        .toList();
        List<Report> reportsForCustomerLastNameSmithExpected =
                reports
                        .stream()
                        .filter(report -> Objects.equals(report.getCustomer().getLastName(), SMITH))
                        .toList();
        List<Report> reportsForCustomerLastNameDoeExpected =
                reports
                        .stream()
                        .filter(report -> Objects.equals(report.getCustomer().getLastName(), DOE))
                        .toList();

        // Mock repository responses
        Mockito.when(reportRepository.findAll()).thenReturn(reports);
        Mockito.when(reportRepository.findAllByUsername(ADMIN)).thenReturn(reportsForAdminExpected);
        Mockito.when(reportRepository.findAllByUsername(USER)).thenReturn(reportsForUserExpected);
        Mockito.when(reportRepository.findAllByCustomer_LastName(SMITH)).thenReturn(reportsForCustomerLastNameSmithExpected);
        Mockito.when(reportRepository.findAllByCustomer_LastName(DOE)).thenReturn(reportsForCustomerLastNameDoeExpected);

        // ACT
        // find reports with incorrect filterCriteria
        var allReportsResultList = Arrays.asList(
            reportService.findAllBy(null, ""),
            reportService.findAllBy("  ", ADMIN),
            reportService.findAllBy(null, USER),
            reportService.findAllBy("  ", "  "),
            reportService.findAllBy(null, SMITH),
            reportService.findAllBy("users", ADMIN),
            reportService.findAllBy(" users", USER)
        );

        // find reports with correct filterCriteria AND matching keyword
        var reportsForAdmin = reportService.findAllBy(USER_CRITERIA, ADMIN);
        var reportsForUser = reportService.findAllBy(USER_CRITERIA, USER);
        var reportsForCustomerLastNameSmith = reportService.findAllBy(CUSTOMER_CRITERIA, SMITH);
        var reportsForCustomerLastNameDoe = reportService.findAllBy(CUSTOMER_CRITERIA, DOE);

        // find reports with correct filterCriteria AND no matching keyword
        var emptyReportsResultList = Arrays.asList(
            reportService.findAllBy(CUSTOMER_CRITERIA, JIM),
            reportService.findAllBy(USER, SMITH),
            reportService.findAllBy(USER, DOE),
            reportService.findAllBy(CUSTOMER_CRITERIA, ADMIN),
            reportService.findAllBy(CUSTOMER_CRITERIA, USER),
            reportService.findAllBy(USER_CRITERIA, "userAdmin")
        );

        // ASSERT
        // Assertions for incorrect inputs of filterCriteria, will return all reports
        Mockito.verify(reportRepository, Mockito.times(allReportsResultList.size())).findAll();
        for (List<Report> reportList : allReportsResultList) {
            Assertions.assertEquals(reports, reportList);
        }

        // Assertions for correct inputs of filterCriteria and matching keywords
        Assertions.assertEquals(reportsForAdminExpected, reportsForAdmin);
        Assertions.assertEquals(reportsForUserExpected, reportsForUser);
        Assertions.assertEquals(reportsForCustomerLastNameSmithExpected, reportsForCustomerLastNameSmith);
        Assertions.assertEquals(reportsForCustomerLastNameDoeExpected, reportsForCustomerLastNameDoe);

        // Assertions for correct inputs of filterCriteria, but no matching keyword is found
        for (List<Report> reportList : emptyReportsResultList) {
            Assertions.assertTrue(reportList.isEmpty());
        }
    }
}
