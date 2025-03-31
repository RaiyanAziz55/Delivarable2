package com.hotel.demo.service;

import com.hotel.demo.dto.LoginResponse;
import com.hotel.demo.model.Employee;
import com.hotel.demo.repository.EmployeeRepository;
import com.hotel.demo.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // ✅ Create a New Employee
    public Employee createEmployee(Employee employee) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword())); // Hash password
        return employeeRepository.save(employee);
    }

    // ✅ Update Employee
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
    
            if (employeeDetails.getFullName() != null) {
                employee.setFullName(employeeDetails.getFullName());
            }
            if (employeeDetails.getAddress() != null) {
                employee.setAddress(employeeDetails.getAddress());
            }
            if (employeeDetails.getPhone() != null) {
                employee.setPhone(employeeDetails.getPhone());
            }
            if (employeeDetails.getEmail() != null) {
                employee.setEmail(employeeDetails.getEmail());
            }
            if (employeeDetails.getRole() != null) {
                employee.setRole(employeeDetails.getRole());
            }
    
            employee.setManager(employeeDetails.isManager());
    
            if (employeeDetails.getPassword() != null && !employeeDetails.getPassword().isEmpty()) {
                employee.setPassword(passwordEncoder.encode(employeeDetails.getPassword()));
            }
    
            return employeeRepository.save(employee);
        }
        return null;
    }
    

    // ✅ Delete Employee
    public boolean deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ✅ Employee Login
public LoginResponse login(String email, String password) {
    Optional<Employee> employeeOptional = employeeRepository.findByEmail(email);

    if (employeeOptional.isPresent()) {
        Employee employee = employeeOptional.get();
        if (passwordEncoder.matches(password, employee.getPassword())) {
            String token = jwtUtil.generateToken(email);
            return new LoginResponse(token, employee.getId(), employee.getHotel().getId(), employee.getRole());
        }
    }
    return null;
}

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    public List<Employee> getEmployeesByHotelId(Long hotelId) {
        return employeeRepository.findByHotelId(hotelId);
    }
    
    
}
