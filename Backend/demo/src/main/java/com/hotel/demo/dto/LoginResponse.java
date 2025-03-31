package com.hotel.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private Long employeeId;
    private Long hotelId;
    private String role;
}
