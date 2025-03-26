package com.hotel.demo.service;

import com.hotel.demo.model.Booking;
import com.hotel.demo.model.Customer;
import com.hotel.demo.model.Room;
import com.hotel.demo.repository.BookingRepository;
import com.hotel.demo.repository.CustomerRepository;
import com.hotel.demo.repository.RoomRepository;

import java.util.List; // ✅ CORRECT List class
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoomRepository roomRepository;

    /**
     * Creates a new booking for a customer.
     * If the customer does not exist, a new record is created.
     * Ensures room availability before booking.
     */
    public Booking createBooking(Customer customer, Long roomId, LocalDate checkIn, LocalDate checkOut) {
        // Check if customer already exists by email
        Optional<Customer> existingCustomer = customerRepository.findByEmail(customer.getEmail());

        Customer savedCustomer;
        if (existingCustomer.isPresent()) {
            savedCustomer = existingCustomer.get();
        } else {
            customer.setRegistrationDate(LocalDate.now()); // Set registration date for new customers
            savedCustomer = customerRepository.save(customer);
        }

        // Check if room exists
        Room room = roomRepository.findByIdWithHotel(roomId)
        .orElseThrow(() -> new RuntimeException("Room not found"));


        // Ensure room availability before booking
        boolean isAvailable = bookingRepository.isRoomAvailable(roomId, checkIn, checkOut);
        if (!isAvailable) {
            throw new RuntimeException("Room is not available for the selected dates.");
        }

        // Create the booking
        Booking booking = new Booking();
        booking.setCustomer(savedCustomer);
        booking.setRoom(room);
        booking.setBookingDate(LocalDateTime.now()); // ✅ CORRECT
        booking.setCheckInDate(checkIn);
        booking.setCheckOutDate(checkOut);
        booking.setStatus("Pending"); // Default status

        return bookingRepository.save(booking);
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public boolean isRoomAvailable(Long roomId, LocalDate checkIn, LocalDate checkOut) {
        List<Booking> conflicts = bookingRepository.findConflictingBookings(roomId, checkIn, checkOut);
        return conflicts.isEmpty();
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingsByCustomerEmail(String email) {
        return bookingRepository.findByCustomerEmailIgnoreCase(email);
    }

    public Booking updateBookingStatus(Long bookingId, String newStatus) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            booking.setStatus(newStatus);
            return bookingRepository.save(booking);
        } else {
            throw new RuntimeException("Booking not found");
        }
    }
    
    
    
    
}
