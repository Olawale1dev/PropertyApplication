package com.example.property.service;

import com.example.property.dto.PropertyDto;
import com.example.property.entity.Property;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface PropertyService {


   // @Query("select s from property s where s.location =?1")
   public ResponseEntity<List<Property>> findPropertyByLocality(String state, String locality,  String price);

  //  @Query("select s from House s where s.price =?1")
  public ResponseEntity<List<Property>> findPropertyByPrice(String price);

   // @Query("select s from House s where s.type =?1")
   ResponseEntity<List<Property>> findPropertyByType(String type);

     ResponseEntity<Property> save(PropertyDto property);

    public Object findPropertyById(Long id);

    String updateProperty(@PathVariable("id")Long propertyId, Property property);

    //public String saveImagePro(MultipartFile file) throws IOException;

   // void init();

    Resource load(String filename);

    void deleteAll();

    Stream<Path> loadAll();

    public ResponseEntity<List<Property>> getListFiles();

    public ResponseEntity<Resource> getFile(@PathVariable String filename);

    public String saveImagePro(MultipartFile file) throws IOException;


    public ResponseEntity<List<Property>> findPropertyByPurpose(String purpose);

    public ResponseEntity<List<Property>> findTop1PropertyByPurpose(@Param("purpose")String purpose);
    public ResponseEntity<List<Property>> findTop1SellPropertyByPurpose(@Param("purpose")String purpose);
    public ResponseEntity<List<Property>> findTop1ShortLetPropertyByPurpose(@Param("purpose")String purpose);

    public ResponseEntity<List<Property>> findPropertyByStatus(String status);
    public ResponseEntity<List<Property>> findTop1PropertyByStatus(@Param("status")String status);
 public ResponseEntity<List<Property>> findTop1ServicedPropertyByStatus(@Param("status")String status);
    public ResponseEntity<List<Property>> findTop1NewlyBuiltPropertyByStatus(@Param("status")String status);
    /*findTop1NewlyBuiltPropertyByStatus*/
    List<Property> findUrlById(Long id);

    Object findAll();



    Optional<Property> deleteHouse(Long id);

    void init();


}
