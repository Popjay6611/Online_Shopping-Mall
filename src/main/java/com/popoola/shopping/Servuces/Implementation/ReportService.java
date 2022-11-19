package com.popoola.shopping.Servuces.Implementation;

import com.popoola.shopping.Models.Report;
import com.popoola.shopping.Repos.ReportRepo;
import com.popoola.shopping.Servuces.Interfaces.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
public class ReportService implements IReportService {

    @Autowired
    ReportRepo reportRepo;


    @Override
    public List<Report> findAll(LocalDateTime startTime, LocalDateTime endTime) {
        return reportRepo.findAllByCreatedBetween(startTime, startTime);
    }
}
