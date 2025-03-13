package com.hotel.demo.dto;

import com.hotel.demo.model.Customer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingRequest {
    private Customer customer;
    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
