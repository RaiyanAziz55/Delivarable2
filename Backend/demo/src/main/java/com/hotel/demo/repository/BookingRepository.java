package com.hotel.demo.repository;

import com.hotel.demo.model.Booking;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    /**
     * Checks if a room is available for the given date range.
     */
    @Query("SELECT CASE WHEN COUNT(b) = 0 THEN TRUE ELSE FALSE END " +
           "FROM Booking b WHERE b.room.id = :roomId " +
           "AND ((b.checkInDate <= :checkOutDate AND b.checkOutDate >= :checkInDate))")
    boolean isRoomAvailable(@Param("roomId") Long roomId,
                            @Param("checkInDate") LocalDate checkInDate,
                            @Param("checkOutDate") LocalDate checkOutDate);
}
