package com.hotel.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Renting")
public class Renting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "renting_id")
    private Long id;

    @Column(name = "date_in", nullable = false)
    private LocalDateTime dateIn;

    @Column(name = "date_out")
    private LocalDateTime dateOut;

    @Column(name = "payment", nullable = false)
    private double payment;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "cust_id", nullable = false)
    private Customer customer;
}
