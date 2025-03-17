package com.hotel.demo.security;

import com.hotel.demo.model.Employee;
import com.hotel.demo.repository.EmployeeRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    public CustomUserDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Employee> employeeOpt = employeeRepository.findByEmail(email);

        if (employeeOpt.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        Employee employee = employeeOpt.get();

        return User.builder()
                .username(employee.getEmail())
                .password(employee.getPassword()) // Should be encrypted!
                .roles(employee.getRole()) // Roles should be configured properly
                .build();
    }
}
