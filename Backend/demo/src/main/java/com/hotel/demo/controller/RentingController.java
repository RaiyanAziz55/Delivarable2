package com.hotel.demo.controller;

import com.hotel.demo.dto.RentingRequest;
import com.hotel.demo.model.Renting;
import com.hotel.demo.service.RentingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rentings")
public class RentingController {
    @Autowired
    private RentingService rentingService;

    @PostMapping
    public ResponseEntity<?> createWalkInRenting(@RequestBody RentingRequest request) {
        try {
            Renting renting = rentingService.createWalkInRenting(request);
            return ResponseEntity.ok(renting);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/from-booking")
    public ResponseEntity<?> createFromBooking(@RequestBody RentingRequest request) {
    try {
        Renting renting = rentingService.createRentingFromBooking(request);
        return ResponseEntity.ok(renting);
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

@GetMapping
public ResponseEntity<List<Renting>> getAllRentings() {
    return ResponseEntity.ok(rentingService.getAllRentings());
}

@GetMapping("/customer/{email}")
public ResponseEntity<List<Renting>> getRentingsByCustomer(@PathVariable String email) {
    return ResponseEntity.ok(rentingService.getRentingsByCustomerEmail(email));
}

@PutMapping("/{id}/checkout")
public ResponseEntity<?> checkOut(@PathVariable Long id) {
    try {
        Renting updated = rentingService.checkOut(id);
        return ResponseEntity.ok(updated);
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}





}
