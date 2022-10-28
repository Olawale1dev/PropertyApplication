package com.example.property.repository;

import com.example.property.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    List<Blog> findBlogByTagName(String type);

    Optional<Blog> findById(Long id);




}
