package com.popoola.shopping.Controllers;

import com.popoola.shopping.Servuces.Implementation.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    ReportService reportService;

    @GetMapping("/get-daily-report")
    public ResponseEntity<Object> getDailyReports()
    {
        Duration hrs = Duration.of(24, ChronoUnit.HOURS);
        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        LocalDateTime endTime = startTime.minus(hrs);
        return ResponseEntity.ok(reportService.findAll(startTime, endTime));
    }

    @GetMapping("/get-monthly-report")
    public ResponseEntity<Object> getMonthlyReports()
    {
        Duration hrs = Duration.of(1, ChronoUnit.MONTHS);
        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        LocalDateTime endTime = startTime.minus(hrs);
        return ResponseEntity.ok(reportService.findAll(startTime, endTime));
    }
}
