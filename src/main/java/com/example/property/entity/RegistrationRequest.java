package com.example.property.entity;//package com.example.property.entity;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Component
public class RegistrationRequest {

    private  String firstName;
    private  String lastName;
    private  String email;
    private  String password;
    private  String signUpAs;
    private String genderTitle;

}
