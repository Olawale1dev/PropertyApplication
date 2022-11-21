package com.example.property.service;

import com.example.property.dto.BlogDto;
import com.example.property.entity.Blog;
import com.example.property.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.example.property.service.Utility.saveFile;


@Service
public class BlogServiceImpl implements BlogService {
    private static final String UPLOAD_DIRECTORY = "/static/images/";
    @Autowired
    BlogRepository blogRepository;




    public String deleteById(Long Id) {
        Optional<Blog> deletedBlog = blogRepository.findById(Id);
        if (deletedBlog != null) {
            blogRepository.deleteById(Id);
        } else {
            throw new RuntimeException("blog not found");
        }
        return "deleted Successfully";
    }


    public Optional<Blog> findById(Long id) {
     Optional<Blog> oneId = blogRepository.findById(id);
        return oneId;
    }


    @Override
    public ResponseEntity<Blog> save(BlogDto blog) {
        Blog newBlog = new Blog();
        newBlog.setTitle(blog.getTitle());
        newBlog.setDescription(blog.getDescription());
        newBlog.setTagName(blog.getTagName());
        newBlog.setPublisher(blog.getPublisher());
        try {
            newBlog.setUrl(saveImage(blog.getUrl()));
        }catch(IOException e){
            System.out.println(e);
            ResponseEntity.internalServerError();
        }
        newBlog = blogRepository.save(newBlog);
        return ResponseEntity.ok(newBlog);
    }

    @Override
    public ResponseEntity<List<Blog>> findAll() {
        try {
            List<Blog> list = blogRepository.findAll();
            if (list.isEmpty() || list.size() == 0) {
                return new ResponseEntity<List<Blog>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Blog>>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

       /* List<Blog> allBlog = blogRepository.findAll();


        for (Blog blog : allBlog) {
            Long blogId = blog.getId();
            Link selfLink = linkTo(BlogServiceImpl.class).slash(blogId).withSelfRel();
            blog.add(selfLink);
            if(blogRepository.findById(blogId).isPresent() ){
                Link blogLink = linkTo(methodOn(BlogServiceImpl.class).findById(blogId)).withRel("allBlogPost");
                blog.add(blogLink);
            }
        }
        Link link= linkTo(BlogServiceImpl.class).withSelfRel();
        CollectionModel<Blog> result = CollectionModel.of(allBlog, link);
        return result;
*/
    }

    /*private Link slash(Long blogId) {
        return slash(blogId);
    }*/


    @Override
    public ResponseEntity<List<Blog>> findBlogByTagName(String tagName) {
        try {
            List<Blog> list = blogRepository.findBlogByTagName(tagName);
            if (list.isEmpty() || list.size() == 0) {
                return new ResponseEntity<List<Blog>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Blog>>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String updateBlog(@PathVariable("id")Long blogId, Blog blog) {

            Optional<Blog> blog1 = blogRepository.findById(blogId);
            if (blog1.isEmpty()) {
                return "No Content Found";
            }else if(blog1.isPresent()) {
                if(blog.getTitle() != null)
                    blog1.get().setTitle(blog.getTitle());
                if(blog.getDescription() != null)
                    blog1.get().setDescription(blog.getDescription());
                if(blog.getTagName() != null)
                    blog1.get().setTagName(blog.getTagName());
                if(blog.getUrl() != null)
                    blog1.get().setUrl(blog.getUrl());
               Blog blog2= blogRepository.save(blog);
            } return "Updated Successfully";
        }


    public String saveImage(@RequestParam("url") MultipartFile file)
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
    }
}
