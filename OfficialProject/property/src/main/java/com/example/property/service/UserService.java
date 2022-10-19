package com.example.property.service;


import com.example.property.entity.User;

import java.util.Optional;



public interface UserService {
    Optional<User> findByEmail(String email);
    String save(User user);
    String save(String email);

}
