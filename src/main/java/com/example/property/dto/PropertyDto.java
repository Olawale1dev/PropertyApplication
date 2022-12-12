package com.example.property.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public class PropertyDto {

        private String type;

        private String purpose;

        private String status;

        private String bedroomNo;

        private String bathroomNo;

        private String toiletNo;

        private String price;
        private String subType;
        private String Size;

        private String title;

        private String agentName;

        private String description;

        private String agentNumber;

        private MultipartFile url;

        private MultipartFile image1;

        private MultipartFile image2;

        private MultipartFile image3;

        private String locality;

        private String state;

        private String area;
        private String streetName;
        private String youtubeLink;
    }

