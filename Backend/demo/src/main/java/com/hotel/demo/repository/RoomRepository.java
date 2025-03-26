package com.hotel.demo.repository;

import com.hotel.demo.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByHotelId(Long hotelId);

    @Query("SELECT r FROM Room r JOIN FETCH r.hotel WHERE r.id = 2")
    Optional<Room> findByIdWithHotel(@Param("roomId") Long roomId);

    List<Room> findByIdNotIn(List<Long> ids);

    @Query("SELECT r FROM Room r " +
       "JOIN r.amenities a " +
       "WHERE (:capacity IS NULL OR r.capacity = :capacity) " +
       "AND (:view IS NULL OR r.view = :view) " +
       "AND (:wifi IS NULL OR a.wifi = :wifi) " +
       "AND (:ac IS NULL OR a.ac = :ac) " +
       "AND (:pool IS NULL OR a.pool = :pool) " +
       "AND (:gym IS NULL OR a.gym = :gym) " +
       "AND (:spa IS NULL OR a.spa = :spa) " +
       "AND (:parking IS NULL OR a.parking = :parking) " +
       "AND (:fridge IS NULL OR a.fridge = :fridge) " +
       "AND (:coffee IS NULL OR a.coffee = :coffee) " +
       "AND NOT EXISTS (SELECT b FROM Booking b " +
       "                WHERE b.room.id = r.id " +
       "                AND b.checkInDate < :checkOutDate " +
       "                AND b.checkOutDate > :checkInDate)")
    List<Room> searchRooms(
        @Param("capacity") String capacity,
        @Param("view") String view,
        @Param("wifi") Boolean wifi,
        @Param("ac") Boolean ac,
        @Param("pool") Boolean pool,
        @Param("gym") Boolean gym,
        @Param("spa") Boolean spa,
        @Param("parking") Boolean parking,
        @Param("fridge") Boolean fridge,
        @Param("coffee") Boolean coffee,
        @Param("checkInDate") LocalDate checkIn,
        @Param("checkOutDate") LocalDate checkOut
);



    
}
