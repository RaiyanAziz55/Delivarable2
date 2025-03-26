package com.hotel.demo.repository;

import com.hotel.demo.model.HotelChain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelChainRepository extends JpaRepository<HotelChain, Long> {
}
