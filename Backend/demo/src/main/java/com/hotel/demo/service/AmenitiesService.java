package com.hotel.demo.service;

import com.hotel.demo.model.Amenities;
import com.hotel.demo.repository.AmenitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmenitiesService {

    @Autowired
    private AmenitiesRepository amenitiesRepository;

    public Amenities updateAmenitiesByRoomId(Long roomId, Amenities newAmenities) {
        Amenities existing = amenitiesRepository.findByRoomId(roomId);
        if (existing != null) {
            existing.setWifi(newAmenities.isWifi());
            existing.setAc(newAmenities.isAc());
            existing.setPool(newAmenities.isPool());
            existing.setGym(newAmenities.isGym());
            existing.setSpa(newAmenities.isSpa());
            existing.setParking(newAmenities.isParking());
            existing.setFridge(newAmenities.isFridge());
            existing.setCoffee(newAmenities.isCoffee());

            return amenitiesRepository.save(existing);
        }
        return null;
    }
}
