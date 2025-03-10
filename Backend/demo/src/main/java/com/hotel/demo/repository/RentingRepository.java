package com.hotel.demo.repository;

import com.hotel.demo.model.Renting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentingRepository extends JpaRepository<Renting, Long> {
    List<Renting> findByCustomerId(Long customerId);
}
