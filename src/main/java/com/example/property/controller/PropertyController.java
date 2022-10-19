package com.example.property.controller;

import com.example.property.entity.Property;
import com.example.property.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PropertyController {


    @Autowired
    private PropertyService propertyService;


    @GetMapping("/showPostForm")
    public String showPostProperty(Model model){
        model.addAttribute("property", new Property());
        return "Post form";
    }
    @RequestMapping("/post")
    public Property save(@RequestBody Property property){
        Property newProperty=  propertyService.save(property);
        return  newProperty;
    }


    @GetMapping("/properties")
    public ResponseEntity<List<Property>> findAll() {
        ResponseEntity<List<Property>> list = propertyService.findAll();
        return list;

    }

    @GetMapping ("/4/{type}")
    public ResponseEntity<List<Property>> findPropertyByType(@PathVariable String type) {
        ResponseEntity<List<Property>> list = propertyService.findPropertyByType(type);
        return list;
    }

    @GetMapping ("/2/{purpose}")
    public ResponseEntity<List<Property>> findPropertyByPurpose(@PathVariable String purpose) {
        ResponseEntity<List<Property>> list = propertyService.findPropertyByPurpose(purpose);
        return list;
    }

    @GetMapping ("/1/{location}")
    public ResponseEntity<List<Property>> findPropertyByLocation(@PathVariable String location) {
        ResponseEntity<List<Property>> list = propertyService.findPropertyByLocation(location);
        return list;
    }
            @GetMapping("/3/{price}")
        public ResponseEntity<List<Property>> findPropertyByPrice(@PathVariable String price){
                ResponseEntity<List<Property>> list = propertyService.findPropertyByPrice(price);
                return list;
    }
    @PutMapping("update/{title}")
    public Property updateHouse(@RequestBody Property property){
        Property update= propertyService.save(property);
        return update;

    }

    @DeleteMapping("delete/{id}")
    public String deleteHouse(@PathVariable Long id){
        Optional<Property> delete = propertyService.deleteHouse(id);
        return ("Deleted Successfully");
    }

}
