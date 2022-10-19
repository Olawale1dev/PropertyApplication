package com.example.property.repository;

import com.example.property.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface PropertyRepository extends JpaRepository<Property, Long > {


   // @Query("select s from property s where s.location =?1")
    List<Property> findPropertyByLocation(String location);

  //  @Query("select s from House s where s.price =?1")
    List<Property> findPropertyByPrice(String price);

   // @Query("select s from House s where s.type =?1")
    List<Property> findPropertyByType(String type);


    Property save(Property property);


    List<Property> findPropertyByPurpose(String purpose);

}
