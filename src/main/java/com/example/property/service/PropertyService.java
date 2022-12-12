package com.example.property.service;

import com.example.property.dto.PropertyDto;
import com.example.property.entity.Property;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface PropertyService {


   // @Query("select s from property s where s.location =?1")
  public Page<Property> findPropertyByLocality(String state, String locality,  String price, Pageable pageable);

  //  @Query("select s from House s where s.price =?1")
 public Page<Property> findPropertyByPrice(String price, Pageable pageable);

   // @Query("select s from House s where s.type =?1")
  public Page<Property> findPropertyByType(String type, Pageable pageable);

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

    public String saveImagePro(MultipartFile file)
            throws IOException;

    //public String saveImagePro(MultipartFile file) throws IOException;

    //public ResponseEntity saveMultipleImages(@RequestParam("url") MultipartFile[] files)throws IOException;


   public Page<Property> findPropertyByPurposeOrderByIdDesc(String purpose, Pageable pageable);

    public ResponseEntity<List<Property>> findTop1PropertyByPurposeOrderByIdDesc(@Param("purpose")String purpose);
    public ResponseEntity<List<Property>> findTop1SellPropertyByPurposeOrderByIdDesc(@Param("purpose")String purpose);
    public ResponseEntity<List<Property>> findTop1ShortLetPropertyByPurposeOrderByIdDesc(@Param("purpose")String purpose);

   public Page<Property> findPropertyByStatusOrderByIdDesc(String status, Pageable pageable);
    public ResponseEntity<List<Property>> findTop1PropertyByStatusOrderByIdDesc(@Param("status")String status);
 public ResponseEntity<List<Property>> findTop1ServicedPropertyByStatusOrderByIdDesc(@Param("status")String status);
    public ResponseEntity<List<Property>> findTop1NewlyBuiltPropertyByStatusOrderByIdDesc(@Param("status")String status);
    /*findTop1NewlyBuiltPropertyByStatus*/
    List<Property> findUrlById(Long id);

   public Page<Property> findAll( Pageable pageable);



    Optional<Property> deleteHouse(Long id);

    void init();

   /* public ResponseEntity findByPurposeSubTypeBedroomNoPriceAreaContaining
            (@RequestParam(required = false) String purpose, String subType, int bedroomNo, int Price, String area,
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "3") int size);*/

    public Page<Property> searchProperty(String query, Pageable pageable);

    Page<Property> searchProperties(@RequestParam("subType") String subType,
                                    @RequestParam("purpose") String purpose,
                                    @RequestParam("status") String status,
                                    @RequestParam("subType") String area ,
                                    @RequestParam("bedroomNo") String bedroomNo,
                                    @RequestParam("price") String price , Pageable pageable);

   public Page<Property> findByPurpose(String purpose, Pageable pageable);

}
