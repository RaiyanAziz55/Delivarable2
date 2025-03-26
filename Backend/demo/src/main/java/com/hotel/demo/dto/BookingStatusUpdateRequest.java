package com.hotel.demo.dto;

import lombok.Data;

@Data
public class BookingStatusUpdateRequest {
    private String status; // e.g. "Confirmed", "Cancelled", "Checked-In"
}
