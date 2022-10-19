package com.example.property.service;

import com.example.property.entity.Property;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PropertyService {


   // @Query("select s from property s where s.location =?1")
   public ResponseEntity<List<Property>> findPropertyByLocation(String location);

  //  @Query("select s from House s where s.price =?1")
  public ResponseEntity<List<Property>> findPropertyByPrice(String price);

   // @Query("select s from House s where s.type =?1")
   ResponseEntity<List<Property>> findPropertyByType(String type);

    Property  save(Property property);

    public ResponseEntity<List<Property>> findPropertyByPurpose(String purpose);

    ResponseEntity<List<Property>>findAll();

    Optional<Property> deleteHouse(Long id);
}
