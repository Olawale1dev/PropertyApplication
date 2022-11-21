package com.example.property.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogDto  {
    private String title;
    private MultipartFile url;
    private String description;
    private String tagName;
    private String publisher;

}
