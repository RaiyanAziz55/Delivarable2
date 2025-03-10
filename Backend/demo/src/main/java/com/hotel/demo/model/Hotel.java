package com.hotel.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private int starRating;
    private String phone;
    private String email;

    @ManyToOne
    @JoinColumn(name = "hotel_chain_id", nullable = false)
    private HotelChain hotelChain;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Room> rooms;

    @OneToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;
}
