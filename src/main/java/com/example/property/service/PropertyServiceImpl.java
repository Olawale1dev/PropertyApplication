package com.example.property.service;

import com.example.property.entity.Property;
import com.example.property.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
  private PropertyRepository propertyRepository;

    public  Property save(Property property) {
       Property newProperty=  propertyRepository.save(property);
        return newProperty;
    }

   /* public ModelAndView upload(@RequestParam CommonsMultipartFile url, HttpSession session){
        String path = session.getServletContext().getRealPath("/");
        String filename = url.getOriginalFilename();
        System.out.println(path+" "+filename);
        try{
            byte barr[] = url.getBytes();
            BufferedOutputStream bout = new BufferedOutputStream(
                    new FileOutputStream(path+"/"+filename));
            bout.write(barr);
            bout.flush();
            bout.close();

        }catch(Exception e){
            System.out.println(e);}
        return new ModelAndView("upload-success","filename", path+"/"+filename);


    }
*/


    public ResponseEntity<List<Property>> findAll() {
        try{
            List<Property> list = propertyRepository.findAll();
            if(list.isEmpty() || list.size() == 0){
                return new ResponseEntity<List<Property>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Property>>(list, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR );
        }

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

    public ResponseEntity<Property> updateHouse(Property property){
        try{
            return new ResponseEntity<Property>(propertyRepository.save(property), HttpStatus.OK);
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
