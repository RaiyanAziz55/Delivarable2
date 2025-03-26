package com.hotel.demo.controller;

import com.hotel.demo.dto.RoomSearchCriteria;
import com.hotel.demo.model.Amenities;
import com.hotel.demo.model.Room;
import com.hotel.demo.service.AmenitiesService;
import com.hotel.demo.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    
    @Autowired
    private RoomService roomService;

    @Autowired
    private AmenitiesService amenitiesService;

    // Get All Rooms
    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    // Get Room by ID
    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // Create Room (Manager Only)
    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room newRoom = roomService.createRoom(room);
        return ResponseEntity.ok(newRoom);
    }

    // Update Room (Manager Only)
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room roomDetails) {
        Room updatedRoom = roomService.updateRoom(id, roomDetails);
        return updatedRoom != null ? ResponseEntity.ok(updatedRoom) : ResponseEntity.notFound().build();
    }

    // Delete Room (Manager Only)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id) {
        boolean deleted = roomService.deleteRoom(id);
        return deleted ? ResponseEntity.ok("Room deleted successfully.") : ResponseEntity.notFound().build();
    }

    // Get Rooms by Hotel ID
    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<Room>> getRoomsByHotel(@PathVariable Long hotelId) {
        List<Room> rooms = roomService.getRoomsByHotel(hotelId);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Room>> checkAvailability(
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {

    List<Room> availableRooms = roomService.getAvailableRooms(checkIn, checkOut);
    return ResponseEntity.ok(availableRooms);
}
    @PostMapping("/search")
public ResponseEntity<List<Room>> searchRooms(@RequestBody RoomSearchCriteria criteria) {
    List<Room> rooms = roomService.searchRooms(criteria);
    return ResponseEntity.ok(rooms);
}

@PutMapping("/{id}/amenities")
public ResponseEntity<Amenities> updateRoomAmenities(@PathVariable Long id, @RequestBody Amenities updatedAmenities) {
    Amenities result = amenitiesService.updateAmenitiesByRoomId(id, updatedAmenities);
    return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
}


}
