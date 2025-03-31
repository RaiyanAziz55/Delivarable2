package com.hotel.demo.repository;

import com.hotel.demo.model.Booking;

import java.time.LocalDate;
import java.util.List;

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

    
    @Query("SELECT b.room.id FROM Booking b WHERE " +
       "(:checkOut > b.checkInDate AND :checkIn < b.checkOutDate)")
    List<Long> findBookedRoomIdsBetween(@Param("checkIn") LocalDate checkIn, @Param("checkOut") LocalDate checkOut);

    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId AND " +
       "((:checkInDate BETWEEN b.checkInDate AND b.checkOutDate) OR " +
       "(:checkOutDate BETWEEN b.checkInDate AND b.checkOutDate) OR " +
       "(b.checkInDate BETWEEN :checkInDate AND :checkOutDate))")
    List<Booking> findConflictingBookings(@Param("roomId") Long roomId,
                                      @Param("checkInDate") LocalDate checkInDate,
                                      @Param("checkOutDate") LocalDate checkOutDate);

    List<Booking> findByCustomerEmailIgnoreCase(String email);

   @Query("SELECT b FROM Booking b WHERE b.room.hotel.id = :hotelId")
   List<Booking> findAllByHotelId(@Param("hotelId") Long hotelId);




}
