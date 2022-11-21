package com.example.property.repository;

import com.example.property.entity.Property;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class PropertyRepositoryTest {
    @Autowired
   private PropertyRepository propertyRepository;

    /*@Test
    void findPropertyByLocation() {
    }

    @Test
    void findPropertyByPrice() {
    }

    @Test
    void findPropertyByType() {
    }*/

    @Test
    void save() {
        Property property = Property
                .builder()
                .agentName("Olawale")
                .agentNumber("07088300300")
                .description("THIS IS A DEPLEX HOUSE LOCATED IN AJA WHICH GOES FOR #3000,000,000 IF YOU ARE INTERESTED PS CONTACT ME")
                .price("90,000,000,000,000,000")
                .title("Mansion House")
                .type("Mansion")
                .build();
        propertyRepository.save(property);
    }
}