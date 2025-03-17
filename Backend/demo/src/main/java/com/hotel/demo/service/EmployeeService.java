package com.hotel.demo.service;

import com.hotel.demo.model.Employee;
import com.hotel.demo.model.Hotel;
import com.hotel.demo.repository.EmployeeRepository;
import com.hotel.demo.repository.HotelRepository;
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
    private HotelRepository hotelRepository;  // 🔹 Add this line

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // ✅ Employee Registration
    public Employee registerEmployee(Employee employee) {
        // 🔹 Ensure hotel exists before assigning
        if (employee.getHotel() != null && employee.getHotel().getId() != null) {
            Hotel hotel = hotelRepository.findById(employee.getHotel().getId())
                                         .orElseThrow(() -> new RuntimeException("Hotel not found"));
            employee.setHotel(hotel);
        }

        // 🔹 Hash password before saving
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));

        return employeeRepository.save(employee);
    }


    public String login(String email, String password) {
        Optional<Employee> employeeOptional = employeeRepository.findByEmail(email);

        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            if (passwordEncoder.matches(password, employee.getPassword())) {
                return jwtUtil.generateToken(email); // 🔹 Return JWT token instead of plain text
            }
        }
        return "Invalid email or password";
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

}
