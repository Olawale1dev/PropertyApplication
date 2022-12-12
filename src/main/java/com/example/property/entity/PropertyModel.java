package com.example.property.entity;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class PropertyModel extends RepresentationModel<PropertyModel> {
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

    private String url;

    private String image1;

    private String image2;

    private String image3;

    private String locality;

    private String state;

    private String area;
    private String streetName;
    private String youtubeLink;
}
