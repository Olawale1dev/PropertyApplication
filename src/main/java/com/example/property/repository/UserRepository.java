package com.example.property.repository;

import com.example.property.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long >{


   // @Query("SELECT u FROM User u WHERE u.email =?1")

    User findByEmail(String email);



   // User save(User user);

   // public String save(Optional<User> user);


  //  Optional<User> save(String email);

    public User findByResetPasswordToken(String token);



    String getByResetPasswordToken(String token);


    void resetPasswordToken(String token);
}
