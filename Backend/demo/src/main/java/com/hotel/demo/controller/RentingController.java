package com.hotel.demo.controller;

import com.hotel.demo.model.Renting;
import com.hotel.demo.service.RentingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rentings")
public class RentingController {
    @Autowired
    private RentingService rentingService;

    @GetMapping
    public List<Renting> getAllRentings() {
        return rentingService.getAllRentings();
    }

    @GetMapping("/{id}")
    public Optional<Renting> getRentingById(@PathVariable Long id) {
        return rentingService.getRentingById(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<Renting> getRentingsByCustomer(@PathVariable Long customerId) {
        return rentingService.getRentingsByCustomer(customerId);
    }

    @PostMapping
    public Renting createRenting(@RequestBody Renting renting) {
        return rentingService.createRenting(renting);
    }

    @DeleteMapping("/{id}")
    public void deleteRenting(@PathVariable Long id) {
        rentingService.deleteRenting(id);
    }
}
