package com.hotel.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "amenities")
public class Amenities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "amenity_id")
    private Long id;

    private boolean wifi;
    private boolean ac;
    private boolean pool;
    private boolean gym;
    private boolean spa;
    private boolean parking;
    private boolean fridge;
    private boolean coffee;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    @JsonBackReference  // ✅ Add this annotation
    private Room room;
}
