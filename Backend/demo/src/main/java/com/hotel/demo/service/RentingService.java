package com.hotel.demo.service;

import com.hotel.demo.dto.RentingRequest;
import com.hotel.demo.model.Booking;
import com.hotel.demo.model.Customer;
import com.hotel.demo.model.Employee;
import com.hotel.demo.model.Renting;
import com.hotel.demo.model.Room;
import com.hotel.demo.repository.BookingRepository;
import com.hotel.demo.repository.CustomerRepository;
import com.hotel.demo.repository.EmployeeRepository;
import com.hotel.demo.repository.RentingRepository;
import com.hotel.demo.repository.RoomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RentingService {
    @Autowired
    private RentingRepository rentingRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Renting createWalkInRenting(RentingRequest request) {
    Room room = roomRepository.findById(request.getRoomId())
        .orElseThrow(() -> new RuntimeException("Room not found"));

    boolean isOccupied = rentingRepository.findActiveRentingByRoom(room.getId()).isPresent();
    if (isOccupied) throw new RuntimeException("Room is currently occupied.");
    Customer customer = customerRepository.findByEmail(request.getCustomer().getEmail())
        .orElseGet(() -> {
            Customer newCustomer = request.getCustomer();
            newCustomer.setRegistrationDate(LocalDate.now()); // ✅ Set registration date
            return customerRepository.save(newCustomer);
        });


    Employee employee = employeeRepository.findById(request.getEmployeeId())
        .orElseThrow(() -> new RuntimeException("Employee not found"));

    Renting renting = new Renting();
    renting.setRoom(room);
    renting.setCustomer(customer);
    renting.setEmployee(employee);
    renting.setDateIn(LocalDateTime.now());
    renting.setDateOut(request.getDateOut());
    renting.setPayment(request.getPayment());

    return rentingRepository.save(renting);
}

    public Renting createRentingFromBooking(RentingRequest request) {
    Booking booking = bookingRepository.findById(request.getBookingId())
        .orElseThrow(() -> new RuntimeException("Booking not found"));

    if (!"Confirmed".equalsIgnoreCase(booking.getStatus())) {
        throw new RuntimeException("Booking must be confirmed to check-in.");
    }

    boolean isOccupied = rentingRepository.findActiveRentingByRoom(booking.getRoom().getId()).isPresent();
    if (isOccupied) throw new RuntimeException("Room is currently occupied.");

    Renting renting = new Renting();
    renting.setBooking(booking);
    renting.setRoom(booking.getRoom());
    renting.setCustomer(booking.getCustomer());
    renting.setEmployee(employeeRepository.findById(request.getEmployeeId())
        .orElseThrow(() -> new RuntimeException("Employee not found")));
    renting.setPayment(request.getPayment());
    renting.setDateIn(LocalDateTime.now());
    renting.setDateOut(request.getDateOut());

    // ✅ Update booking status to Checked-In
    booking.setStatus("Checked-In");
    bookingRepository.save(booking);

    return rentingRepository.save(renting);
}

public List<Renting> getAllRentings() {
    return rentingRepository.findAll();
}

public List<Renting> getRentingsByCustomerEmail(String email) {
    return rentingRepository.findByCustomerEmailIgnoreCase(email);
}

public Renting checkOut(Long rentingId) {
    Renting renting = rentingRepository.findById(rentingId)
        .orElseThrow(() -> new RuntimeException("Renting not found"));

    if (renting.getDateOut() != null) {
        throw new RuntimeException("Rental already checked out.");
    }

    renting.setDateOut(LocalDateTime.now());

    // Optionally update booking status
    if (renting.getBooking() != null) {
        renting.getBooking().setStatus("Checked-Out");
        bookingRepository.save(renting.getBooking());
    }

    return rentingRepository.save(renting);
}

public List<Renting> getRentingsByHotelId(Long hotelId) {
    return rentingRepository.findByHotelId(hotelId);
}






}
