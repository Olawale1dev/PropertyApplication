package com.example.property.repository;

import com.example.property.entity.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface PropertyRepository extends PagingAndSortingRepository<Property, Long > {


   // @Query("select s from property s where s.location =?1")
    Page<Property> findPropertyByLocality( String locality, Pageable pageable);

  //  @Query("select s from House s where s.price =?1")
    Page<Property> findPropertyByPrice(String price, Pageable pageable);

   // @Query("select s from House s where s.type =?1")
    Page<Property> findPropertyByType(String type, Pageable pageable);


    Property save(Property property);

        Page<Property>findByPurpose(String purpose, Pageable pageable);
    Page<Property> findPropertyByPurposeOrderByIdDesc(String purpose, Pageable pageable);
    Page<Property> findPropertyByStatusOrderByIdDesc(String status, Pageable pageable);

    List<Property> findTop1PropertyByStatusOrderByIdDesc(String status, Pageable topOne);

 List<Property> findTop1ServicedPropertyByStatusOrderByIdDesc(String status, Pageable topOne);

    List<Property> findTop1NewlyBuiltPropertyByStatusOrderByIdDesc(String status, Pageable topOne);


 //List<Property> findTop1PropertyByPurpose(String purpose, Pageable topOne);


    List<Property> findTop1PropertyByPurposeOrderByIdDesc(String purpose, Pageable topOne);
    //List<Property> findTop1PropertyByPurpose(String purpose, Pageable topOne, Sort sort);

    //class.findTop1PropertyByPurpose("fdgfd", psg, Sort(Sort.Direction.DESC,"id"))
    List<Property> findTop1SellPropertyByPurposeOrderByIdDesc(String purpose, Pageable topOne);

    List<Property> findTop1ShortLetPropertyByPurposeOrderByIdDesc(String purpose, Pageable topOne);

    Property findPropertyByTitle(Property title);


    List<Property> findUrlById(Long id);

    //Stream<Path> loadAll();
    //Resource load(String filename);

    //void init();

    void deleteAll();

    Page<Property> findByPurposeContaining(String purpose, String subType, String area, Pageable pageable);

    @Query(value = "SELECT p FROM Property p WHERE " +
            "p.purpose LIKE CONCAT('%',:query, '%')" +
            "Or p.subType LIKE CONCAT('%', :query, '%')" +
            "Or p.bedroomNo LIKE CONCAT('%', :query, '%')" +
            "Or p.id Like CONCAT('%', :query, '%')"+
            //"Or p.price LIKE CONCAT('%', :price, '%')" +
            "Or p.area LIKE CONCAT('%', :query, '%') ORDER BY price ASC")
    Page<Property> searchProperty(String query, Pageable pageable);

    @Query("SELECT p FROM  Property p WHERE " +
            "p.subType=:subType " +
            "Or p.purpose=:purpose Or p.area=:area " +
            "Or p.status=:status Or p.bedroomNo=:bedroomNo Or p.price=:price ORDER BY p.price DESC "  )
    Page<Property> searchProperties(@RequestParam("subType") String subType,
                                    @RequestParam("purpose") String purpose,
                                    @RequestParam("status") String status,
                                    @RequestParam("subType") String area ,
                                    @RequestParam("bedroomNo") String bedroomNo,
                                    @RequestParam("price") String price , Pageable pageable);
    }
