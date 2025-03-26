package com.hotel.demo.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RoomSearchCriteria {
    private String capacity;
    private String view;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    // Amenity filters
    private Boolean wifi;
    private Boolean ac;
    private Boolean pool;
    private Boolean gym;
    private Boolean spa;
    private Boolean parking;
    private Boolean fridge;
    private Boolean coffee;

    // Getters and setters
}

