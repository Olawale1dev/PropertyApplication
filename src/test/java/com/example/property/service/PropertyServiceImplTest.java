package com.example.property.service;

import com.example.property.repository.PropertyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PropertyServiceImplTest {

    @Autowired
   private PropertyRepository propertyRepository;

    @Test
    void findAll() {
        propertyRepository.findAll();
    }
}