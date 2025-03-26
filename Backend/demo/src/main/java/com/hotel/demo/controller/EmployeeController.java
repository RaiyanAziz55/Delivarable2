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

    // ✅ Employee Login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = employeeService.login(request.getEmail(), request.getPassword());

        if (token != null) {
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(401).body("Invalid email or password");
    }

    // ✅ Create Employee (Manager Only)
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        System.out.println("📢 Reached Create Controller!");
        Employee newEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.ok(newEmployee);
    }

    // ✅ Update Employee (Manager Only)
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
        return updatedEmployee != null ? ResponseEntity.ok(updatedEmployee) : ResponseEntity.notFound().build();
    }

    // ✅ Delete Employee (Manager Only)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        boolean deleted = employeeService.deleteEmployee(id);
        return deleted ? ResponseEntity.ok("Employee deleted successfully.") : ResponseEntity.notFound().build();
    }

    // ✅ Get All Employees (Manager Only)
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        System.out.println("📢 Reached getAllEmployees Controller!");
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
}
