package com.hotel.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;
    
    private String address;
    private String phone;
    private String email;

    @Column(name = "id_type")
    private String idType;

    @Column(name = "reg_date")
    private LocalDate registrationDate;
}
