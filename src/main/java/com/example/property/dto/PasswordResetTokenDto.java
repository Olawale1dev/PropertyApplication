package com.example.property.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetTokenDto {

    private String  User;

    private String token;
}
