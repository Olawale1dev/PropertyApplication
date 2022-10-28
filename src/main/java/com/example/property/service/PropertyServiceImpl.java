package com.example.property.service;

import com.example.property.entity.Property;
import com.example.property.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PropertyServiceImpl implements PropertyService {
    public static final String UPLOAD_DIRECTORY = "/images" ;
    @Autowired
  private PropertyRepository propertyRepository;

    public  Property save(Property property) {
       Property newProperty=  propertyRepository.save(property);
        return newProperty;
    }

    public ModelAndView save(@RequestParam CommonsMultipartFile url, HttpSession session)
    throws Exception{
        ServletContext context= session.getServletContext();
        String path = context.getRealPath(UPLOAD_DIRECTORY);
        String filename = url.getOriginalFilename();
        System.out.println(path+" "+filename);
        try{
            byte barr[] = url.getBytes();
            BufferedOutputStream bout = new BufferedOutputStream(
                    new FileOutputStream(new File(path+File.separator+filename)));
            bout.write(barr);
            bout.flush();
            bout.close();

        }catch(Exception e){
            System.out.println(e);}
        return new ModelAndView("File successfully saved");


    }


    public CollectionModel<Property> findAll() {

            List<Property> list = propertyRepository.findAll();
            if (list.isEmpty() || list.size() == 0) {
                String no_content = "No ContentAvailable";
                return null;
            }

            for (Property property : list) {
                property.add(linkTo(methodOn(PropertyServiceImpl.class).getOne(property.getId())).withSelfRel());
            }
            CollectionModel<Property> collectionModel = CollectionModel.of(list);

            collectionModel.add(linkTo(methodOn(PropertyServiceImpl.class).findAll()).withSelfRel());

            return collectionModel;
        }


    private Long getOne(Long id) {
        return id;
    }

    @Override
    public ResponseEntity<List<Property>> findPropertyByLocation(String location) {
        try {
            List<Property> list = propertyRepository.findPropertyByLocation(location);
            if (list.isEmpty() || list.size() == 0) {
                return new ResponseEntity<List<Property>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Property>>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        @Override
        public ResponseEntity<List<Property>> findPropertyByPrice(String price){
            try{
                List<Property> listOfPrice = propertyRepository.findPropertyByPrice(price);
                if(listOfPrice.isEmpty()|| listOfPrice.size()==0){
                    return new ResponseEntity<List<Property>>(HttpStatus.NO_CONTENT);
                }
                return  new ResponseEntity<List<Property>>(listOfPrice, HttpStatus.OK);
            }catch(Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    @Override
    public ResponseEntity<List<Property>> findPropertyByType(String type) {
        try {
            List<Property> list = propertyRepository.findPropertyByType(type);
            if (list.isEmpty() || list.size() == 0) {
                return new ResponseEntity<List<Property>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Property>>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @Override
    public ResponseEntity<List<Property>> findPropertyByPurpose( String purpose) {
        try {
            List<Property> list = propertyRepository.findPropertyByPurpose(purpose);
            if (list.isEmpty() || list.size() == 0) {
                return new ResponseEntity<List<Property>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Property>>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Property> updateHouse(Property title){
        try{
            Property property1;
            property1 = propertyRepository.findPropertyByTitle(title);
            property1.add(linkTo(methodOn(Property.class).getTitle()).withSelfRel());
            return new ResponseEntity<Property>(propertyRepository.save(title), HttpStatus.OK);
        } catch(Exception e){
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public Optional<Property> deleteHouse(Long id){
        Optional<Property> deletedProperty= propertyRepository.findById(id);
        if(deletedProperty.isPresent()){
            propertyRepository.deleteById(id);
        }else{
            throw new RuntimeException("property not found");
        }
        return deletedProperty;
    }
}
