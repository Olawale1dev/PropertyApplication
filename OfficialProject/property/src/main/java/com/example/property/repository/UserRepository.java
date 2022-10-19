package com.example.property.repository;

import com.example.property.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long >{


   // @Query("SELECT u FROM User u WHERE u.email =?1")

    Optional<User> findByEmail(String email);

    User save(User user);

    public String save(Optional<User> user);


    Optional<User> save(String email);


}
