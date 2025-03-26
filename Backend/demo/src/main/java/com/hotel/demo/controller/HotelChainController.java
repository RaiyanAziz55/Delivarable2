package com.hotel.demo.controller;

import com.hotel.demo.model.HotelChain;
import com.hotel.demo.service.HotelChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotel-chains")
public class HotelChainController {

    @Autowired
    private HotelChainService hotelChainService;

    @GetMapping
    public List<HotelChain> getAllChains() {
        return hotelChainService.getAllChains();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelChain> getChainById(@PathVariable Long id) {
        Optional<HotelChain> chain = hotelChainService.getChainById(id);
        return chain.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<HotelChain> createChain(@RequestBody HotelChain chain) {
        return ResponseEntity.ok(hotelChainService.createChain(chain));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelChain> updateChain(@PathVariable Long id, @RequestBody HotelChain chainDetails) {
        HotelChain updated = hotelChainService.updateChain(id, chainDetails);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChain(@PathVariable Long id) {
        hotelChainService.deleteChain(id);
        return ResponseEntity.noContent().build();
    }
}
