package com.example.property.controller;

import com.example.property.dto.PropertyDto;
import com.example.property.entity.Property;
import com.example.property.entity.PropertyModel;
import com.example.property.entity.PropertyModelAssembler;
import com.example.property.repository.PropertyRepository;
import com.example.property.service.PropertyService;
import com.example.property.service.PropertyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/property")
public class PropertyController {


    @Autowired
    private PropertyService propertyService;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyModelAssembler propertyModelAssembler;

    @Autowired
    private PagedResourcesAssembler<Property> pagedResourcesAssembler;


    @RequestMapping(value= "/search-purpose", method= {RequestMethod.POST, RequestMethod.GET})
    public Page<Property> showSearch(@ModelAttribute("property") Property purpose , Model model, Pageable pageable){
        Page<Property> prop= propertyService.findByPurpose(purpose.getPurpose(), pageable);
        model.addAttribute("property",prop);
        return prop;
    }
    @PostMapping( "/post" )
    public ResponseEntity<Property> save(@ModelAttribute ("property") PropertyDto property){
        System.out.println("Am inside property Controller");
        ResponseEntity<Property> newProperty=  propertyService.save(property);
        return  newProperty;
    }


    @GetMapping("/find-all")
    public Page<Property> findAll(Pageable pageable) {
        Page<Property> list = propertyRepository.findAll(pageable);
        return list;

    }

    @GetMapping ("/type/{type}")
    public Page<Property> findPropertyByType(@PathVariable String type, Pageable pageable) {
        Page<Property> list = propertyService.findPropertyByType(type, pageable);
        return list;
    }

    @GetMapping ("/status/{status}")
    public Page<Property> findPropertyByStatusOrderByIdDesc(@PathVariable String status, Pageable pageable) {
        Page<Property> list = propertyService.findPropertyByStatusOrderByIdDesc(status, pageable);
        return list;
    }
    /**/
    @GetMapping("top1/Furnished/{status}")
    public ResponseEntity<List<Property>> findTop1PropertyByStatusOrderByIdDesc(@PathVariable("status")String status, Pageable topOne){
        ResponseEntity<List<Property>> top1= propertyService.findTop1PropertyByStatusOrderByIdDesc(status);
        return top1;
    }
    @GetMapping("top1/Serviced/{status}")
    public ResponseEntity<List<Property>> findTop1ServicedPropertyByStatusOrderByIdDesc(@PathVariable("status")String status, Pageable topOne){
        ResponseEntity<List<Property>> top1= propertyService.findTop1ServicedPropertyByStatusOrderByIdDesc(status);
        return top1;
    }
    @GetMapping("top1/NewlyBuilt/{status}")
    public ResponseEntity<List<Property>> findTop1NewlyBuiltPropertyByStatusOrderByIdDesc(@PathVariable("status")String status, Pageable topOne){
        ResponseEntity<List<Property>> top1= propertyService.findTop1NewlyBuiltPropertyByStatusOrderByIdDesc(status);
        return top1;
    }
    /*findTop1NewlyBuiltPropertyByStatus*/
    @GetMapping("top1/{purpose}")
    public ResponseEntity<List<Property>> findTop1PropertyByPurpose(@PathVariable("purpose")String purpose, Pageable topOne){
        ResponseEntity<List<Property>> top1= propertyService.findTop1PropertyByPurposeOrderByIdDesc(purpose);
        return top1;

    }

    @GetMapping("top1/Sell/{purpose}")
    public ResponseEntity<List<Property>> findTop1SellPropertyByPurpose(@PathVariable("purpose")String purpose, Pageable topOne){
        ResponseEntity<List<Property>> top1= propertyService.findTop1SellPropertyByPurposeOrderByIdDesc(purpose);
        return top1;

    }

    @GetMapping("top1/ShortLet/{purpose}")
    public ResponseEntity<List<Property>> findTop1ShortLetPropertyByPurposeOrderByIdDesc(@PathVariable("purpose")String purpose, Pageable topOne){
        ResponseEntity<List<Property>> top1= propertyService.findTop1ShortLetPropertyByPurposeOrderByIdDesc(purpose);
        return top1;

    }

    @GetMapping ("/purpose/{purpose}")
    public Page<Property> findPropertyByPurpose(@PathVariable String purpose, Pageable pageable) {
        Page<Property> list = propertyService.findPropertyByPurposeOrderByIdDesc(purpose, pageable);
        return list;
    }

    @GetMapping ("/{location}")
    public Page<Property> findPropertyByLocality(@PathVariable String state, String locality,  String price, Pageable pageable) {
        Page<Property> list = propertyService.findPropertyByLocality(state, locality, price, pageable);
        return list;
    }

    @GetMapping("/one/{id}")
    public Optional<Property> findPropertyBy(@PathVariable Long id){

        Optional<Property> list = propertyRepository.findById(id);
        return list;
    }
            @GetMapping("/{price}")
        public Page<Property> findPropertyByPrice(@PathVariable String price, Pageable pageable){
                Page<Property> list = propertyService.findPropertyByPrice(price, pageable);
                return list;
    }

    @GetMapping("/search-property")
    public ResponseEntity findByPurposeSubTypeBedroomNoPriceAreaContaining(
            @RequestParam(required = false) String purpose,
            String subType, String area,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        try {
            List<Property> properties = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);
            Page<Property> pageTut;
            if (purpose == null || subType == null || area == null) {
                pageTut = propertyRepository.findAll(paging);
            } else {
                pageTut = propertyRepository.findByPurposeContaining
                        (purpose, subType, area, paging);
                properties = pageTut.getContent();
                Map<String, Object> response = new HashMap<>();
                response.put("properties", properties);
                response.put("currentPage", pageTut.getNumber());
                response.put("totalProperties", pageTut.getTotalElements());
                response.put("totalPages", pageTut.getTotalPages());
                return new ResponseEntity<>(pageTut, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Property>> searchProperty(@RequestParam("query") String query, Pageable pageable){
        return ResponseEntity.ok(propertyService.searchProperty(query,pageable));
    }

    @GetMapping("/search-prop")
    public ResponseEntity<Page<Property>> searchProperties(@RequestParam (value = "subType" ,required=false )  String subType ,
                                                           @RequestParam(value = "purpose",required=false) String purpose,
                                                           @RequestParam(value = "status",required=false) String status,
                                                           @RequestParam(value = "subType",required=false) String area ,
                                                           @RequestParam(value = "bedroomNo",required=false)   String bedroomNo,
                                                           @RequestParam(value = "price",required=false) String price , Pageable pageable){
        return ResponseEntity.ok(propertyService.searchProperties(subType, purpose, status,area,bedroomNo,price, pageable));
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


   /* @GetMapping("/url")
    public String  url()
    {
        return "url";
    }
*/
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

    @GetMapping("/api/{purpose}")
    public PagedModel<PropertyModel> findByPurpose(@PathVariable String purpose, Pageable pageable){
        Page<Property> propertyPage = propertyService.findByPurpose(purpose, pageable);
        return pagedResourcesAssembler.toModel(propertyPage, propertyModelAssembler);
    }

}
