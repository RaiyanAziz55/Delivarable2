package com.hotel.demo.controller;

import com.hotel.demo.dto.LoginRequest;
import com.hotel.demo.model.Employee;
import com.hotel.demo.service.EmployeeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String response = employeeService.login(request.getEmail(), request.getPassword());

        if (response.equals("Login successful!")) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).body(response);  // Unauthorized if login fails
    }

      // ✅ Employee Registration Endpoint
    @PostMapping("/register")
    public ResponseEntity<Employee> register(@RequestBody Employee employee) {
        Employee registeredEmployee = employeeService.registerEmployee(employee);
        return ResponseEntity.ok(registeredEmployee);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        System.out.println("📢 Reached getAllEmployees Controller!");
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }
}
