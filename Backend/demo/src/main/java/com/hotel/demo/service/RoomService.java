package com.hotel.demo.service;

import com.hotel.demo.dto.RoomSearchCriteria;
import com.hotel.demo.model.Room;
import com.hotel.demo.repository.BookingRepository;
import com.hotel.demo.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    public List<Room> getRoomsByHotel(Long hotelId) {
        return roomRepository.findByHotelId(hotelId);
    }

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, Room roomDetails) {
        return roomRepository.findById(id).map(room -> {
            room.setRoomNumber(roomDetails.getRoomNumber());
            room.setPrice(roomDetails.getPrice());
            room.setCapacity(roomDetails.getCapacity());
            room.setExtended(roomDetails.isExtended());
            room.setProblems(roomDetails.getProblems());
            return roomRepository.save(room);
        }).orElse(null);
    }

    public boolean deleteRoom(Long id) {
        if (roomRepository.existsById(id)) {
            roomRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public List<Room> getAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
    List<Long> bookedRoomIds = bookingRepository.findBookedRoomIdsBetween(checkIn, checkOut);
    return roomRepository.findByIdNotIn(bookedRoomIds);
}

    public List<Room> searchRooms(RoomSearchCriteria criteria) {
    return roomRepository.searchRooms(
        criteria.getCapacity(),
        criteria.getView(),
        criteria.getWifi(),
        criteria.getAc(),
        criteria.getPool(),
        criteria.getGym(),
        criteria.getSpa(),
        criteria.getParking(),
        criteria.getFridge(),
        criteria.getCoffee(),
        criteria.getCheckInDate(),
        criteria.getCheckOutDate()
    );
}


}
