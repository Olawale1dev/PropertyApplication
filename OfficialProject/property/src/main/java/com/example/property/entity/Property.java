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
public class Property {

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
    @Column(name="property_type")
    private String type;
    @Column(name="purpose")
    private String purpose;
    @Column(name="status")
    private String status;
    @Column(name="bedroom_number")
    private String bedroomNo;
    @Column(name="bathroom_No")
    private String bathroomNo;
    @Column(name="toilet_number")
    private String toiletNo;
    @Column(name="price")
    private String price;
    @Column(name="title")
    private String title;
    @Column(name="agent_name")
    private String agentName;
    @Column(name="description")
    private String description;
    @Column(name="agent_number")
    private String agentNumber;
    @Column(name="url")
    private String url;
    @Column(name="location")
    private String location;
    @Column(name="locate_state")
    private String state;
    @Column(name="locate_area")
    private String area;
    @Column(name="locate_street")
    private String streetName;
}