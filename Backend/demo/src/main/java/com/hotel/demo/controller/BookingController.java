package com.hotel.demo.controller;

import com.hotel.demo.dto.BookingRequest;
import com.hotel.demo.model.Booking;
import com.hotel.demo.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    /**
     * Endpoint to create a new booking.
     */
    @PostMapping
    public Booking bookRoom(@RequestBody BookingRequest bookingRequest) {
        return bookingService.createBooking(bookingRequest.getCustomer(),
                bookingRequest.getRoomId(),
                bookingRequest.getCheckInDate(),
                bookingRequest.getCheckOutDate());
    }

    /**
     * Endpoint to fetch a booking by ID.
     */
    @GetMapping("/{id}")
    public Optional<Booking> getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }
}
