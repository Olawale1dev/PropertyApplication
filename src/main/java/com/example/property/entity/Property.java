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
public class Property extends RepresentationModel<Property> {

    @Id
    @SequenceGenerator(
            name="property_sequence",
            sequenceName="property_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "property_sequence"
    )
    private Long id;
    @Column(name="property_type", nullable = false)
    private String type;
    @Column(name="purpose", nullable = false)
    private String purpose;
    @Column(name="status", nullable = false)
    private String status;
    @Column(name="bedroom_number", nullable = false)
    private String bedroomNo;
    @Column(name="bathroom_No", nullable = false)
    private String bathroomNo;
    @Column(name="toilet_number", nullable = false)
    private String toiletNo;
    @Column(name="price", nullable = false)
    private String price;
    @Column(name="title", nullable = false)
    private String title;
    @Column(name="agent_name", nullable = false)
    private String agentName;
    @Column(name="description", nullable = false)
    private String description;
    @Column(name="agent_number", nullable = false)
    private String agentNumber;
    @Column(name="url", nullable = false)
    private CommonsMultipartFile url;
    @Column(name="location", nullable = false)
    private String location;
    @Column(name="locate_state", nullable = false)
    private String state;
    @Column(name="locate_area", nullable = false)
    private String area;
    @Column(name="locate_street", nullable = false)
    private String streetName;
}
