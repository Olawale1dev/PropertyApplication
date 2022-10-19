package com.example.property.service;

import com.example.property.entity.Blog;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface BlogService {

    public Blog save(Blog blog);

    public ResponseEntity<List<Blog>> findAll();

    public ResponseEntity<List<Blog>> findBlogByTagName(String tagName);

    public Blog updateBlog(Blog blog);

    Optional<Blog> findById(Long id);

    Optional<Blog> deleteById(Long id);
}
