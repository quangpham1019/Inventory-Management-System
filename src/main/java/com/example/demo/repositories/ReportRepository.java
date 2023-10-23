package com.example.demo.repositories;

import com.example.demo.domain.Customer;
import com.example.demo.domain.Report;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAllByCustomer_LastName(String customerLastName);
    List<Report> findAllByUserEmail(String userEmail);
}
