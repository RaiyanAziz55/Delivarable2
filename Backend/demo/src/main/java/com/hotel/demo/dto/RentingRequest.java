package com.hotel.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.hotel.demo.model.Customer;

import lombok.Data;

@Data
public class RentingRequest {
    private Long bookingId; // optional
    private Long roomId;
    private Long employeeId;
    private Customer customer;
    private LocalDateTime dateOut;
    private double payment;
}
