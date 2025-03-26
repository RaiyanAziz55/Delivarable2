package com.hotel.demo.controller;

import com.hotel.demo.dto.BookingRequest;
import com.hotel.demo.dto.BookingStatusUpdateRequest;
import com.hotel.demo.model.Booking;
import com.hotel.demo.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
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

    @GetMapping("/availability")
    public ResponseEntity<Boolean> checkRoomAvailability(
        @RequestParam Long roomId,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate) {

    boolean available = bookingService.isRoomAvailable(roomId, checkInDate, checkOutDate);
    return ResponseEntity.ok(available);
}

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/customer/{email}")
    public ResponseEntity<List<Booking>> getBookingsByCustomer(@PathVariable String email) {
        return ResponseEntity.ok(bookingService.getBookingsByCustomerEmail(email));
    }

    @PutMapping("/{id}/status")
public ResponseEntity<?> updateBookingStatus(
        @PathVariable Long id,
        @RequestBody BookingStatusUpdateRequest request) {

    try {
        Booking updatedBooking = bookingService.updateBookingStatus(id, request.getStatus());
        return ResponseEntity.ok(updatedBooking);
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}




}
