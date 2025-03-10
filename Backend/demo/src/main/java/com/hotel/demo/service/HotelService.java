package com.hotel.demo.service;

import com.hotel.demo.model.Hotel;
import com.hotel.demo.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Optional<Hotel> getHotelById(Long id) {
        return hotelRepository.findById(id);
    }

    public Hotel createHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Hotel updateHotel(Long id, Hotel hotelDetails) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(id);
        if (hotelOptional.isPresent()) {
            Hotel hotel = hotelOptional.get();
            hotel.setAddress(hotelDetails.getAddress());
            hotel.setStarRating(hotelDetails.getStarRating());
            hotel.setPhone(hotelDetails.getPhone());
            hotel.setEmail(hotelDetails.getEmail());
            return hotelRepository.save(hotel);
        }
        return null;
    }

    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }
}
