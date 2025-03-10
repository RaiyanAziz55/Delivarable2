package com.hotel.demo.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class DatabaseQueryService {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<Object[]> testDatabaseQuery() {
        return entityManager.createNativeQuery("SELECT * FROM HotelChain").getResultList();
    }
}
