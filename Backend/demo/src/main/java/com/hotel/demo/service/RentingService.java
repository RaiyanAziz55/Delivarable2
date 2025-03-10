package com.hotel.demo.service;

import com.hotel.demo.model.Renting;
import com.hotel.demo.repository.RentingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentingService {
    @Autowired
    private RentingRepository rentingRepository;

    public List<Renting> getAllRentings() {
        return rentingRepository.findAll();
    }

    public Optional<Renting> getRentingById(Long id) {
        return rentingRepository.findById(id);
    }

    public List<Renting> getRentingsByCustomer(Long customerId) {
        return rentingRepository.findByCustomerId(customerId);
    }

    public Renting createRenting(Renting renting) {
        return rentingRepository.save(renting);
    }

    public void deleteRenting(Long id) {
        rentingRepository.deleteById(id);
    }
}
