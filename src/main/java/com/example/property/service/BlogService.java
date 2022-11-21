package com.example.property.service;

import com.example.property.dto.BlogDto;
import com.example.property.entity.Blog;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BlogService {

    public ResponseEntity<Blog> save(BlogDto blog);
    public String saveImage(MultipartFile file) throws IOException;

    public ResponseEntity<List<Blog>> findAll();

    public ResponseEntity<List<Blog>> findBlogByTagName(String tagName);

    public String updateBlog(@PathVariable("id")Long blogId, Blog blog);

    public Optional<Blog> findById(Long id);

    String deleteById(Long id);
}
