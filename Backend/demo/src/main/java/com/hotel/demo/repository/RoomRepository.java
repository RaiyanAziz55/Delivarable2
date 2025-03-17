package com.hotel.demo.repository;

import com.hotel.demo.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByHotelId(Long hotelId);

    @Query("SELECT r FROM Room r JOIN FETCH r.hotel WHERE r.id = 2")
    Optional<Room> findByIdWithHotel(@Param("roomId") Long roomId);
}
