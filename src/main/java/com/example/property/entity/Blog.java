package com.example.property.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Blog {


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
    @Column(name="post_title")
    private String title;
    @Column(name="url_title")
    private String[] url;
    @Column(name="post_description")
    private String description;
    @Column(name="post_tagName")
    private String tagName;
    @Column(name="post_publisher")
    private String publisher;
}
