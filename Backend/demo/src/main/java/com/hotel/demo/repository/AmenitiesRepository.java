package com.hotel.demo.repository;

import com.hotel.demo.model.Amenities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenitiesRepository extends JpaRepository<Amenities, Long> {
    Amenities findByRoomId(Long roomId);
}
