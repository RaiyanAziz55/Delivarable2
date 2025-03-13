package com.hotel.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HID")
    private Long id;

    private String name;
    private String address;
    private String phone;
    private String category;

    @Column(name = "star_count") // ✅ Correct field name
    private int starCount;

    @ManyToOne
    @JoinColumn(name = "chain_id", nullable = false)
    private HotelChain hotelChain;
}
