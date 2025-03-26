package com.hotel.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;
    
    private String address;
    private String phone;

    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String role;
    private boolean manager;
    
    @Column(name = "ssn_number")
    private String ssnNumber;


    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // ✅ Add this
    private Hotel hotel;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "position")
    private String position;
}
