package com.thenextone.app.dashboard;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @GetMapping("/summary")
    public ResponseEntity<String> fetchDashboardSummary() {
        return new ResponseEntity<String>("Hello Bojack horseman", HttpStatus.OK);
    }
}
