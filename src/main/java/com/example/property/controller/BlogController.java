package com.example.property.controller;

import com.example.property.entity.Blog;
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
public class BlogController {
    @Autowired
  private BlogService blogService;


    @CrossOrigin
    @RequestMapping(value = "/1$/post", method =RequestMethod.POST)
    public Blog save(@ModelAttribute Blog blog) {
        Blog newBlog=  blogService.save(blog);
        return newBlog;
    }

    @CrossOrigin
    @GetMapping("/2$/blog")
    public ResponseEntity<List<Blog>> findAllPost() {
        ResponseEntity<List<Blog>> list = blogService.findAll();
        return list;

    }
    @CrossOrigin
    @GetMapping("/3$/{tagName}")
    public ResponseEntity<List<Blog>> findBlogByTagName(@PathVariable String tagName) {
        ResponseEntity<List<Blog>> list = blogService.findBlogByTagName(tagName);
        return  list;
    }

    @CrossOrigin
    @GetMapping("/$0/{id}")
    public Optional<Blog> findById(@PathVariable Long id){
            Optional<Blog> findById = blogService.findById(id);
            return findById;
        }

    @CrossOrigin
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


