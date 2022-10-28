package com.example.property.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Blog extends RepresentationModel<Blog> {


    @javax.persistence.Id
    @SequenceGenerator(
            name="blog_sequence",
            sequenceName="blog_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "blog_sequence"
    )
    private Long id;
    @Column(name="post_title", nullable = false)
    private String title;
    @Column(name="url", nullable = false)
    private CommonsMultipartFile url;
    @Column(name="post_description", nullable = false)
    private String description;
    @Column(name="post_tagName", nullable = false)
    private String tagName;
    @Column(name="post_publisher", nullable=false)
    private String publisher;
}
