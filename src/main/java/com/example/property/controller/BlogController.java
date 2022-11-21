package com.example.property.controller;

import com.example.property.dto.BlogDto;
import com.example.property.entity.Blog;
import com.example.property.repository.BlogRepository;
import com.example.property.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("/blog")
public class BlogController {
    @Autowired
  private BlogService blogService;

    @Autowired
    private BlogRepository blogRepository;

    @PostMapping("/post")
    public ResponseEntity<Blog> save(@ModelAttribute("blog") BlogDto blog) {
        System.out.println("Am inside blog controller");
        ResponseEntity<Blog> newBlog =  blogService.save(blog);
        return newBlog;
    }

    @CrossOrigin
    @GetMapping("/find-all-post")
    public ResponseEntity<List<Blog>> findAllPost() {
        ResponseEntity<List<Blog>> list = blogService.findAll();
        return list;

    }

    @GetMapping("/find-by/{tagName}")
    public ResponseEntity<List<Blog>> findBlogByTagName(@PathVariable String tagName) {
        ResponseEntity<List<Blog>> list = blogService.findBlogByTagName(tagName);
        return  list;
    }


    @GetMapping("/find-by-id/{id}")
    public Optional<Blog> findById(@PathVariable Long id){
          Optional<Blog> postId = blogService.findById(id);

            return postId;
        }

    private Class getOne(Long id) {
        return getOne(id);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable("id") Long blogId, @RequestBody BlogDto blog) {
        ResponseEntity<Blog> update = blogService.save(blog);
        return update;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBlog(@PathVariable Long id){
        String delete= blogService.deleteById(id);
        return "deleted successfully";
    }

    @GetMapping("/form")
    public String showBlogForm(Model model) {
        model.addAttribute("blog", new Blog());
        return "Post form";
    }
}


