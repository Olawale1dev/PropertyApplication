package com.example.property.service;

import com.example.property.entity.RegistrationRequest;
import com.example.property.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Setter
@Getter
public  abstract  class RegistrationService {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private RegistrationRequest registrationRequest;

    public RegistrationService() {

    }

    public String register(RegistrationRequest request) {

        return userServiceImpl.save(
                new User(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        request.getGenderTitle(),
                        request.getSignUpAs()

                )
        );

    }

    public String save(User request) {
        return userServiceImpl.save(request);
    }


}

