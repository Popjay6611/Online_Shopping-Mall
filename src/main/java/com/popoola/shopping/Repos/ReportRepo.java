package com.popoola.shopping.Repos;

import com.popoola.shopping.Models.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ReportRepo extends JpaRepository<Report, Integer> {
    List<Report> findAllByCreatedBetween(LocalDateTime CreatedDateStart, LocalDateTime CreatedDateEnd);
}
