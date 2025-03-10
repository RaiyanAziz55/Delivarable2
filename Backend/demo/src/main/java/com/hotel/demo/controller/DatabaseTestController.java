package com.hotel.demo.controller;

import com.hotel.demo.service.DatabaseQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/test-db")
public class DatabaseTestController {
    
    @Autowired
    private DatabaseQueryService databaseQueryService;

    @GetMapping("/hotels")
    public List<Object[]> testDatabase() {
        return databaseQueryService.testDatabaseQuery();
    }
}
