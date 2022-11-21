package com.example.property.repository;

import com.example.property.entity.Property;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long > {


   // @Query("select s from property s where s.location =?1")
    List<Property> findPropertyByLocality( String locality);

  //  @Query("select s from House s where s.price =?1")
    List<Property> findPropertyByPrice(String price);

   // @Query("select s from House s where s.type =?1")
    List<Property> findPropertyByType(String type);


    Property save(Property property);


    List<Property> findPropertyByPurpose(String purpose);
    List<Property> findPropertyByStatus(String status);

    List<Property> findTop1PropertyByStatus(String status, Pageable topOne);

 List<Property> findTop1ServicedPropertyByStatus(String status, Pageable topOne);

    List<Property> findTop1NewlyBuiltPropertyByStatus(String status, Pageable topOne);


 List<Property> findTop1PropertyByPurpose(String purpose, Pageable topOne);
    List<Property> findTop1SellPropertyByPurpose(String purpose, Pageable topOne);

    List<Property> findTop1ShortLetPropertyByPurpose(String purpose, Pageable topOne);

    Property findPropertyByTitle(Property title);

    List<Property> findUrlById(Long id);

    //Stream<Path> loadAll();
    //Resource load(String filename);

    //void init();

    void deleteAll();
}
