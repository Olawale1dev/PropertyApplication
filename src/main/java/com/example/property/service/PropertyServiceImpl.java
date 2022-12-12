package com.example.property.service;

import com.example.property.config.InternalPropertyFile;
import com.example.property.dto.PropertyDto;
import com.example.property.entity.Property;
import com.example.property.entity.PropertyModelAssembler;
import com.example.property.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
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
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

@Service
public class PropertyServiceImpl implements PropertyService {
    public static final String UPLOAD_DIRECTORY = "images/" ;

    @Autowired
    private InternalPropertyFile internalPropertyFile;

    private Properties prop= InternalPropertyFile.properties();

    private String uploads = prop.getProperty("filePath");
    public  final Path  root = Paths.get(uploads);
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

    @Autowired
    private PropertyModelAssembler propertyModelAssembler;

    @Autowired
    private PagedResourcesAssembler<Property> pagedResourcesAssembler;


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




  /*  public String getUploads() {
        return uploads;
    }

    public void setUploads(String uploads) {
        this.uploads = uploads;
    }
*/
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
            newProperty.setImage1(saveImagePro(property.getImage1()));
            newProperty.setImage2(saveImagePro(property.getImage2()));
            newProperty.setImage3(saveImagePro(property.getImage3()));

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
            //throw new RuntimeException("Could not initialize folder for upload!");
            System.out.println("Could not initialize folder for upload! \n cause by:");
            System.out.println(e);
        }
    }

    @Override
    public Page<Property> searchProperty(String query, Pageable pageable) {
        Page<Property> properties = propertyRepository.searchProperty(query, pageable);
        return properties;
    }

    @Override
    public Page<Property> searchProperties(@RequestParam("subType") String subType,
                                           @RequestParam("purpose") String purpose,
                                           @RequestParam("status") String status,
                                           @RequestParam("subType") String area ,
                                           @RequestParam("bedroomNo") String bedroomNo,
                                           @RequestParam("price") String price , Pageable pageable) {
        Page<Property> proper = propertyRepository.searchProperties(subType,purpose, status,area,bedroomNo,price, pageable);
        if(proper.isEmpty() || proper == null){
            return  null;
        }
        return proper;
    }

    @Override
    public Page<Property> findByPurpose(String purpose, Pageable pageable) {
       // Pageable pagea = PageRequest.of(0,3);
        return propertyRepository.findByPurpose(purpose, pageable);
    }


   /* @Override
    public ResponseEntity  findByPurposeContaining(
            @RequestParam(required = false) String purpose,
            String subType, int bedroomNo, int price, String area,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        try {
            List<Property> properties = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);
            Page<Property> pageTut;
            if (purpose == null || subType == null || bedroomNo == 0 || price == 0 || area == null) {
                pageTut = propertyRepository.findAll(paging);
            } else {
                pageTut = propertyRepository.findByPurposeContaining
                        (purpose, subType, bedroomNo, price, area, paging);
                properties = pageTut.getContent();
                Map<String, Object> response = new HashMap<>();
                response.put("properties", properties);
                response.put("currentPage", pageTut.getNumber());
                response.put("totalItems", pageTut.getTotalElements());
                response.put("totalPages", pageTut.getTotalPages());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/

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

//    /* public String saveImagePro(@RequestParam( defaultValue = "url") String url,
//                               @RequestParam( defaultValue = "image1") String image1,
//                               @RequestParam( defaultValue = "image2") String image2,
//                               @RequestParam( defaultValue = "image3") String image3, MultipartFile file)
//            throws IOException*/
    public String saveImagePro(MultipartFile file)
            throws IOException {
        String message = "";


        try {
            //byte[] bytes = file.getBytes();
            List<String> fileNames = new ArrayList<>();
            for(int i = 0; i<fileNames.size(); i++){
                if(fileNames.size() > 1){
                   // MultipartFile url = files[i];

                }
            }
            Arrays.asList(file).stream().forEach(files -> {
                try {
                    Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
               //while(file.length > 1)
                fileNames.add(file.getOriginalFilename());
            });

            message = "static"+ File.separator+ file.getOriginalFilename();

            return (message);

        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public ResponseEntity saveMultipleImages(@RequestParam("url") MultipartFile[] files)
            throws IOException{
        List<String> fileDownloadUrls = new ArrayList<>();
        Arrays.asList(files).stream().forEach(file -> {
            try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
            //while(files.length > 1)
              fileDownloadUrls.add(file.getOriginalFilename());
        });
        message = "static"+ File.separator+ file.getOriginalFilename();

        //return  message;
        return  ResponseEntity.ok(message);
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


    public Page<Property> findAll(Pageable pageable) {
       // Pageable pagea = PageRequest.of(0, 3);
        Page<Property> propertyPage = propertyRepository.findAll(pageable);

            return propertyPage;
        }




    public Long getOne(Long id) {
        return id;
    }

    @Override
    public Page<Property> findPropertyByLocality(String state, String locality, String price, Pageable pageable) {
        try {
            Page<Property> list = propertyRepository.findPropertyByLocality( locality, pageable);
            if (list.isEmpty()) {
                return null;
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

        @Override
        public Page<Property> findPropertyByPrice(String price, Pageable pageable){
            try{
                Page<Property> listOfPrice = propertyRepository.findPropertyByPrice(price, pageable);
                if(listOfPrice.isEmpty()){
                    return null;
                }
                return  listOfPrice;
            }catch(Exception e) {
                return null;
            }
        }

    @Override
    public Page<Property> findPropertyByType(String type, Pageable pageable) {
        try {
            Page<Property> list = propertyRepository.findPropertyByType(type, pageable);
            if (list.isEmpty()) {
                return null;
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }



    @Override
    public Page<Property> findPropertyByPurposeOrderByIdDesc( String purpose, Pageable pageable) {
        try {
           Page<Property> list = propertyRepository.findPropertyByPurposeOrderByIdDesc(purpose, pageable);
            if (list.isEmpty()) {
                return null;
            }
            return list;
        } catch (Exception e) {
            return  null;
        }
    }

    @Override
    public ResponseEntity<List<Property>> findTop1PropertyByPurposeOrderByIdDesc(String purpose) {
        try {
            Pageable topThree = PageRequest.of(0,1);
            List<Property> list = propertyRepository.findTop1PropertyByPurposeOrderByIdDesc("For-Rent", topThree);
            if (list.isEmpty() || list.size() == 0) {
                return new ResponseEntity<List<Property>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Property>>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Property>> findTop1SellPropertyByPurposeOrderByIdDesc(String purpose) {
        try {
            Pageable top1 = PageRequest.of(0, 1 );
            List<Property> list = propertyRepository.findTop1SellPropertyByPurposeOrderByIdDesc("For-Sell", top1);
            if (list.isEmpty() || list.size() == 0) {
                return new ResponseEntity<List<Property>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Property>>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Property>> findTop1ShortLetPropertyByPurposeOrderByIdDesc(String purpose) {
        try {
            Pageable top1 = PageRequest.of(0, 1);
            List<Property> list = propertyRepository.findTop1ShortLetPropertyByPurposeOrderByIdDesc("Short-Let", top1);
            if (list.isEmpty() || list.size() == 0) {
                return new ResponseEntity<List<Property>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Property>>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Page<Property> findPropertyByStatusOrderByIdDesc(String status, Pageable pageable) {
        try {
            Page<Property> list = propertyRepository.findPropertyByStatusOrderByIdDesc(status, pageable);
            if (list.isEmpty()) {
                return null;
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ResponseEntity<List<Property>> findTop1PropertyByStatusOrderByIdDesc(String status) {
        try {
            Pageable top1 = PageRequest.of(0, 1);
            List<Property> list = propertyRepository.findTop1PropertyByStatusOrderByIdDesc("Furnished", top1);
            if (list.isEmpty() || list.size() == 0) {
                return new ResponseEntity<List<Property>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Property>>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Property>> findTop1ServicedPropertyByStatusOrderByIdDesc(String status) {
        try {
            Pageable top1 = PageRequest.of(0, 1);
            List<Property> list = propertyRepository.findTop1ServicedPropertyByStatusOrderByIdDesc("Serviced", top1);
            if (list.isEmpty() || list.size() == 0) {
                return new ResponseEntity<List<Property>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Property>>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Property>> findTop1NewlyBuiltPropertyByStatusOrderByIdDesc(String status) {
        try {
            Pageable top1 = PageRequest.of(0, 1);
            List<Property> list = propertyRepository.findTop1NewlyBuiltPropertyByStatusOrderByIdDesc("Newly Built", top1);
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
