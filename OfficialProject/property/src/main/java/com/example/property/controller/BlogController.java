package com.example.property.controller;

import com.example.property.entity.Blog;
import com.example.property.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BlogController {
    @Autowired
  private BlogService blogService;



    @PostMapping("/1$/post")
    public Blog save(Blog blog) {
        Blog newBlog=  blogService.save(blog);
        return newBlog;
    }


    @GetMapping("/2$/blog")
    public ResponseEntity<List<Blog>> findAll() {
        ResponseEntity<List<Blog>> list = blogService.findAll();
        return list;

    }

    @GetMapping("/3$/{tagName}")
    public ResponseEntity<List<Blog>> findBlogByTagName(@PathVariable String tagName) {
        ResponseEntity<List<Blog>> list = blogService.findBlogByTagName(tagName);
        return  list;
    }


    @GetMapping("/$0/{id}")
    public Optional<Blog> findById(@PathVariable Long id){
            Optional<Blog> findById = blogService.findById(id);
            return findById;
        }


    @PutMapping("/4$/{title}")
    public Blog updateBlog(@RequestBody Blog blog) {
        Blog update = blogService.save(blog);
        return update;
    }

    @DeleteMapping("/5$/{id}")
    public String deleteBlog(@PathVariable Long id){
        Optional<Blog> delete= blogService.deleteById(id);
        return "deleted successfully";
    }

    @GetMapping("/$6/showBlogForm")
    public String showPostProperty(Model model) {
        model.addAttribute("blog", new Blog());
        return "Post form";
    }
}


