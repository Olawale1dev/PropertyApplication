package com.example.property.controller;

import com.example.property.dto.PropertyDto;
import com.example.property.entity.Property;
import com.example.property.repository.PropertyRepository;
import com.example.property.service.PropertyService;
import com.example.property.service.PropertyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/property")
public class PropertyController {


    @Autowired
    private PropertyService propertyService;

    @Autowired
    private PropertyRepository propertyRepository;


    @GetMapping("/form")
    public String showPostProperty(Model model){
        model.addAttribute("property", new Property());
        return "Post form";
    }
    @PostMapping( "/post" )
    public ResponseEntity<Property> save(@ModelAttribute ("property") PropertyDto property){
        System.out.println("Am inside Blog Controller");
        ResponseEntity<Property> newProperty=  propertyService.save(property);
        return  newProperty;
    }


    @GetMapping("/find-all")
    public List<Property> findAll() {
        List<Property> list = propertyRepository.findAll();
        return list;

    }

    @GetMapping ("/type/{type}")
    public ResponseEntity<List<Property>> findPropertyByType(@PathVariable String type) {
        ResponseEntity<List<Property>> list = propertyService.findPropertyByType(type);
        return list;
    }

    @GetMapping ("/status/{status}")
    public ResponseEntity<List<Property>> findPropertyByStatus(@PathVariable String status) {
        ResponseEntity<List<Property>> list = propertyService.findPropertyByStatus(status);
        return list;
    }
    /**/
    @GetMapping("top1/Furnished/{status}")
    public ResponseEntity<List<Property>> findTop1PropertyByStatus(@PathVariable("status")String status, Pageable topOne){
        ResponseEntity<List<Property>> top1= propertyService.findTop1PropertyByStatus(status);
        return top1;
    }
    @GetMapping("top1/Serviced/{status}")
    public ResponseEntity<List<Property>> findTop1ServicedPropertyByStatus(@PathVariable("status")String status, Pageable topOne){
        ResponseEntity<List<Property>> top1= propertyService.findTop1ServicedPropertyByStatus(status);
        return top1;
    }
    @GetMapping("top1/NewlyBuilt/{status}")
    public ResponseEntity<List<Property>> findTop1NewlyBuiltPropertyByStatus(@PathVariable("status")String status, Pageable topOne){
        ResponseEntity<List<Property>> top1= propertyService.findTop1NewlyBuiltPropertyByStatus(status);
        return top1;
    }
    /*findTop1NewlyBuiltPropertyByStatus*/
    @GetMapping("top1/{purpose}")
    public ResponseEntity<List<Property>> findTop1PropertyByPurpose(@PathVariable("purpose")String purpose, Pageable topOne){
        ResponseEntity<List<Property>> top1= propertyService.findTop1PropertyByPurpose(purpose);
        return top1;

    }

    @GetMapping("top1/Sell/{purpose}")
    public ResponseEntity<List<Property>> findTop1SellPropertyByPurpose(@PathVariable("purpose")String purpose, Pageable topOne){
        ResponseEntity<List<Property>> top1= propertyService.findTop1SellPropertyByPurpose(purpose);
        return top1;

    }

    @GetMapping("top1/ShortLet/{purpose}")
    public ResponseEntity<List<Property>> findTop1ShortLetPropertyByPurpose(@PathVariable("purpose")String purpose, Pageable topOne){
        ResponseEntity<List<Property>> top1= propertyService.findTop1ShortLetPropertyByPurpose(purpose);
        return top1;

    }





    @GetMapping ("/purpose/{purpose}")
    public ResponseEntity<List<Property>> findPropertyByPurpose(@PathVariable String purpose) {
        ResponseEntity<List<Property>> list = propertyService.findPropertyByPurpose(purpose);
        return list;
    }

    @GetMapping ("/{location}")
    public ResponseEntity<List<Property>> findPropertyByLocality(@PathVariable String state, String locality,  String price) {
        ResponseEntity<List<Property>> list = propertyService.findPropertyByLocality(state, locality, price);
        return list;
    }

    @GetMapping("/one/{id}")
    public Optional<Property> findPropertyBy(@PathVariable Long id){

        Optional<Property> list = propertyRepository.findById(id);
        return list;
    }
            @GetMapping("/{price}")
        public ResponseEntity<List<Property>> findPropertyByPrice(@PathVariable String price){
                ResponseEntity<List<Property>> list = propertyService.findPropertyByPrice(price);
                return list;
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Property> updateHouse(@PathVariable("id")Long id, @RequestBody PropertyDto property){
       ResponseEntity<Property> update= propertyService.save(property);
        return update;

    }

    @DeleteMapping("/delete/{id}")
    public String deleteHouse(@PathVariable Long id){
        Optional<Property> delete = propertyService.deleteHouse(id);
        return ("Deleted Successfully");
    }


    @GetMapping("/url")
    public String  url()
    {
        return "url";
    }

   /* public static final String BASE_PATH = "images/";

    @RequestMapping(value = "/{fileName}" , method = RequestMethod.GET)
    public ResponseEntity<FileSystemResource> getFile(@PathVariable("fileName") String fileName) {
        FileSystemResource resource = new FileSystemResource(new File(BASE_PATH, fileName));
        ResponseEntity<FileSystemResource> responseEntity = new ResponseEntity<>(resource, HttpStatus.OK);
        return responseEntity;
    }*/


    @GetMapping("/files")
    public ResponseEntity<List<Property>> getListFiles() {
        List<Property> properties = propertyService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(PropertyServiceImpl.class, "getFile", path.getFileName().toString()).build().toString();

            return new Property(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(properties);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String url){
        Resource file = propertyService.load(url);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }



}
