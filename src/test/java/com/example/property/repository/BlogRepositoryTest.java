package com.example.property.repository;

import com.example.property.entity.Blog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class BlogRepositoryTest {

    @Autowired
    private BlogRepository blogRepository;
    @Test
    void save() {
        Blog blog = Blog
                .builder()
                .title("olawale is now a Best developer and expert developer and one of the best developer in the world")
                .publisher("Olawale")
                .tagName("News")
                .description("Sodiq is now a Best and expert developer and one of the best developer in the world")
                .build();
        blogRepository.save(blog);
    }
}