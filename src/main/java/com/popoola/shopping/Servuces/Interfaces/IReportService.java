package com.popoola.shopping.Servuces.Interfaces;

import com.popoola.shopping.Models.Report;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface IReportService {
    List<Report> findAll(LocalDateTime startTime, LocalDateTime endTime);

}
