package com.hotel.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelChain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chainName;
    private String hqAddress;
    private String email;
    private String phone;
    private int hotelCount;

    @OneToMany(mappedBy = "hotelChain", cascade = CascadeType.ALL)
    private List<Hotel> hotels;
}
