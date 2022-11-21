package com.example.property.service;

import com.example.property.dto.PropertyDto;
import com.example.property.entity.Property;
import com.example.property.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PropertyServiceImpl implements PropertyService {
    public static final String UPLOAD_DIRECTORY = "images/" ;

    private final Path root = Paths.get("uploads");
    @Autowired
  private PropertyRepository propertyRepository;
   /* @Autowired
    private PropertyService propertyService;
*/


    @Autowired
    private HttpSession s;

    @Autowired
    private Model mod;


    //private JdbcTemplate jdbcTemp;

    private Path rootLocation;
    private CommonsMultipartFile CommonsMultipartFile;
    private MultipartFile multipartFile;
    private MultipartFile file;
    @Autowired
    private String message;


    /*public void PropertyServiceImpl(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.rootLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public PropertyServiceImpl(Path rootLocation) {
        this.rootLocation = rootLocation;
    }*/


    /*public PropertyServiceImpl(DataSource dataSource) {
        jdbcTemp = new JdbcTemplate(dataSource);
    }*/

    public ResponseEntity<Property> save(PropertyDto property) {
        Property newProperty = new Property();
        newProperty.setLocality(property.getLocality());
        newProperty.setType(property.getType());
        newProperty.setStatus(property.getStatus());
        newProperty.setState(property.getState());
        newProperty.setToiletNo(property.getToiletNo());
        newProperty.setStreetName(property.getStreetName());
        newProperty.setPurpose(property.getPurpose());
        newProperty.setPrice(property.getPrice());
        newProperty.setArea(property.getArea());
        newProperty.setBedroomNo(property.getBedroomNo());
        newProperty.setBathroomNo(property.getBathroomNo());
        newProperty.setAgentNumber(property.getAgentNumber());
        newProperty.setDescription(property.getDescription());
        newProperty.setAgentName(property.getAgentName());
        newProperty.setTitle(property.getTitle());
        newProperty.setSize(property.getSize());
        newProperty.setSubType((property.getSubType()));
        newProperty.setYoutubeLink(property.getYoutubeLink());
        try {

            newProperty.setUrl(saveImagePro(property.getUrl()));

        } catch (IOException e) {

        }

        newProperty=  propertyRepository.save(newProperty);
        return  ResponseEntity.ok(newProperty);

    }

    @Override
    public void init() {
        try {
            Files.createDirectory(root);

        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    @Override
    public ResponseEntity<List<Property>> getListFiles() {
        return getListFiles();
    }

    public String saveImagePro(@RequestParam("url") MultipartFile file)
            throws IOException {
        String message = "";

        try {
            List<String> fileNames = new ArrayList<>();

            Arrays.asList(file).stream().forEach(files -> {
                try {
                    Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //propertyRepository.save(Property);
                fileNames.add(file.getOriginalFilename());
            });



            message = root +"/"+ file.getOriginalFilename();
            return (message);

        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

   /* public ResponseEntity<List<Property>> getListFiles() {
        List<Property> properties = propertyService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String urls = MvcUriComponentsBuilder
                    .fromMethodName(PropertyServiceImpl.class, "getFile", path.getFileName().toString()).build().toString();

            return new Property(filename, urls);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(properties);
    }*/

    @Override
    public ResponseEntity<Resource> getFile(String filename) {
        return getFile(filename);
    }




    /*public String saveImagePro(@RequestParam("url") MultipartFile file)
            throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        System.out.println("multipart original: "+file.getOriginalFilename());
        System.out.println("treated filename: "+ filename);
        try{
            saveFile(UPLOAD_DIRECTORY, filename,
                    file);
        }catch (IOException e){
            throw new IOException("could not save file "+filename, e);
        }

        return UPLOAD_DIRECTORY+filename;
    }*/


    @Override
    public List<Property> findUrlById(Long id) {
        List<Property> list = propertyRepository.findUrlById(id);
        if(list.isEmpty() || list.size()==0){
            String no_image= "No image available";
        }
        return list;
        /*String query = "select url from property where id=?";
        Property url = jdbcTemp.queryForObject(query, new Object[] {id}, Property.class);
        return null;*/
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




    public Long getOne(Long id) {
        return id;
    }

    @Override
    public ResponseEntity<List<Property>> findPropertyByLocality(String state, String locality,  String price) {
        try {
            List<Property> list = propertyRepository.findPropertyByLocality( locality);
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

    @Override
    public ResponseEntity<List<Property>> findTop1PropertyByPurpose(String purpose ) {
        try {
            Pageable topThree = PageRequest.of(0, 1);
            List<Property> list = propertyRepository.findTop1PropertyByPurpose("For-Rent", topThree);
            if (list.isEmpty() || list.size() == 0) {
                return new ResponseEntity<List<Property>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Property>>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Property>> findTop1SellPropertyByPurpose(String purpose) {
        try {
            Pageable top1 = PageRequest.of(0, 1);
            List<Property> list = propertyRepository.findTop1SellPropertyByPurpose("For-Sell", top1);
            if (list.isEmpty() || list.size() == 0) {
                return new ResponseEntity<List<Property>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Property>>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Property>> findTop1ShortLetPropertyByPurpose(String purpose) {
        try {
            Pageable top1 = PageRequest.of(0, 1);
            List<Property> list = propertyRepository.findTop1SellPropertyByPurpose("Short-Let", top1);
            if (list.isEmpty() || list.size() == 0) {
                return new ResponseEntity<List<Property>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Property>>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Property>> findPropertyByStatus(String status) {
        try {
            List<Property> list = propertyRepository.findPropertyByStatus(status);
            if (list.isEmpty() || list.size() == 0) {
                return new ResponseEntity<List<Property>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Property>>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Property>> findTop1PropertyByStatus(String status) {
        try {
            Pageable top1 = PageRequest.of(0, 1);
            List<Property> list = propertyRepository.findTop1PropertyByStatus("Furnished", top1);
            if (list.isEmpty() || list.size() == 0) {
                return new ResponseEntity<List<Property>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Property>>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Property>> findTop1ServicedPropertyByStatus(String status) {
        try {
            Pageable top1 = PageRequest.of(0, 1);
            List<Property> list = propertyRepository.findTop1ServicedPropertyByStatus("Serviced", top1);
            if (list.isEmpty() || list.size() == 0) {
                return new ResponseEntity<List<Property>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Property>>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Property>> findTop1NewlyBuiltPropertyByStatus(String status) {
        try {
            Pageable top1 = PageRequest.of(0, 1);
            List<Property> list = propertyRepository.findTop1NewlyBuiltPropertyByStatus("Newly Built", top1);
            if (list.isEmpty() || list.size() == 0) {
                return new ResponseEntity<List<Property>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Property>>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
        public Object findPropertyById(Long id) {
            try {
                Optional<Property> list = propertyRepository.findById(id);
                if (list == null)  {
                    return new ResponseEntity<Property>(HttpStatus.NO_CONTENT);
                }
                return list;
            } catch (Exception e) {
                return new ResponseEntity<Property>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    public String updateProperty(@PathVariable("id")Long propertyId, Property property){
        Optional<Property> property1 = propertyRepository.findById(propertyId);
        if(property1.isEmpty()){
            return "No Content Found";
        } else if (property1.isPresent()) {
            if(property.getTitle() != null)
                property1.get().setTitle(property.getTitle());
            if(property.getDescription() != null)
                property1.get().setDescription(property.getDescription());
            if(property.getArea() != null)
                property1.get().setArea(property.getArea());
            if(property.getAgentNumber() != null)
                property1.get().setAgentNumber(property.getAgentNumber());
            if(property.getBathroomNo() != null)
                property1.get().setBathroomNo(property.getBathroomNo());
            if(property.getBedroomNo() != null)
                property1.get().setBedroomNo(property.getBedroomNo());
            if(property.getLocality() != null)
                property1.get().setLocality(property.getLocality());
            if(property.getPrice() != null)
                property1.get().setPrice(property.getPrice());
            if(property.getPurpose() != null)
                property1.get().setPurpose(property.getPurpose());
            if(property.getState() != null)
                property1.get().setState(property.getState());
            if(property.getStatus() != null)
                property1.get().setStatus(property.getStatus());
            if(property.getStreetName() != null)
                property1.get().setStreetName(property.getStreetName());
            if(property.getToiletNo() != null)
                property1.get().setToiletNo(property.getToiletNo());
            if(property.getType() != null)
                property1.get().setType(property.getType());
            if(property.getUrl() != null)
                property1.get().setUrl(property.getUrl());
            if(property.getSize() != null)
                property1.get().setSize(property.getSize());
            if(property.getSubType() != null)
                property1.get().setSubType(property.getSubType());
            if(property.getYoutubeLink() !=null )
                property1.get().setYoutubeLink(property.getYoutubeLink());
            Property property2=propertyRepository.save(property);
        }
        return  "Updated Successfully";
    }

    /*public String saveImagePro(@RequestPart("url") MultipartFile file ) throws IOException {
        // Getting bytes of file and
        // storing it in a byte array
        byte[] data = file.getBytes();



        String filePath
                = s.getServletContext().getRealPath("/")
                + "WEB-INF" + File.separator + "resources"
                + File.separator + "image" + File.separator
                + file.getOriginalFilename();

        // Try block to check for exceptions
        try {

            // Creating an object of FileOutputStream class
            FileOutputStream fileOut
                    = new FileOutputStream(filePath);
            fileOut.write(data);

            // Closing connections of file
            // using close() method
            fileOut.close();
            mod.addAttribute("imgName",
                    file.getOriginalFilename());
        }

        // Catch block to handle the exceptions
        catch (Exception e) {

            // Displaying the exception/s along with
            // line number using printStackTrace() method
            e.printStackTrace();
        }

        return filePath;
    }
*/



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

   /* public String saveImagePro(@RequestParam("url") MultipartFile file ) throws IOException{

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try
        {
            if (file.isEmpty())
            {
                throw new RemoteException("Failed to store empty file " + file.getOriginalFilename());
            }
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.rootLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        }
        catch (IOException e)
        {
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
        }

    }

    @Override
    public void init()
    {
        try
        {
            Files.createDirectory(rootLocation);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }*/
}
