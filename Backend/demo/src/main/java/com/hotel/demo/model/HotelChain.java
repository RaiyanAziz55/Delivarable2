package com.hotel.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "HotelChain")
public class HotelChain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HCID")
    private Long id;

    private String name;
    private String address;
    private String phone;

    @Column(name = "chain_number")
    private String chainNumber;
}
