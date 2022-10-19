package com.example.property.service;

import com.example.property.entity.Blog;
import com.example.property.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;


    public Optional<Blog> deleteById(Long id){
        Optional<Blog> deletedBlog= blogRepository.findById(id);
        if(deletedBlog.isPresent()){
            blogRepository.deleteById(id);
        }else{
            throw new RuntimeException("blog not found");
        }
        return deletedBlog;
    }


    public Optional<Blog> findById(Long id) {
        Optional<Blog> oneId = blogRepository.findById(id);
        return oneId;
    }

    @Override
    public Blog save(Blog blog) {
        Blog newBlog = blogRepository.save(blog);
        return newBlog;
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
    }

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

    @Override
    public Blog updateBlog(Blog blog) {
        Blog update = blogRepository.save(blog);
        return update;
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

}
