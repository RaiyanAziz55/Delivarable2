package com.hotel.demo.repository;

import com.hotel.demo.model.Renting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentingRepository extends JpaRepository<Renting, Long> {
    List<Renting> findByCustomerId(Long customerId);

    @Query("SELECT r FROM Renting r WHERE r.room.id = :roomId AND r.dateOut IS NULL")
    Optional<Renting> findActiveRentingByRoom(@Param("roomId") Long roomId);

    List<Renting> findByCustomerEmailIgnoreCase(String email);

    @Query("SELECT r FROM Renting r WHERE r.room.hotel.id = :hotelId")
    List<Renting> findByHotelId(@Param("hotelId") Long hotelId);



}
