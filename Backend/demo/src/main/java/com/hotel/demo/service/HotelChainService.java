package com.hotel.demo.service;

import com.hotel.demo.model.HotelChain;
import com.hotel.demo.repository.HotelChainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelChainService {

    @Autowired
    private HotelChainRepository hotelChainRepository;

    public List<HotelChain> getAllChains() {
        return hotelChainRepository.findAll();
    }

    public Optional<HotelChain> getChainById(Long id) {
        return hotelChainRepository.findById(id);
    }

    public HotelChain createChain(HotelChain chain) {
        return hotelChainRepository.save(chain);
    }

    public HotelChain updateChain(Long id, HotelChain chainDetails) {
        return hotelChainRepository.findById(id).map(chain -> {
            chain.setName(chainDetails.getName());
            chain.setAddress(chainDetails.getAddress());
            chain.setPhone(chainDetails.getPhone());
            chain.setChainNumber(chainDetails.getChainNumber());
            return hotelChainRepository.save(chain);
        }).orElse(null);
    }

    public void deleteChain(Long id) {
        hotelChainRepository.deleteById(id);
    }
}
