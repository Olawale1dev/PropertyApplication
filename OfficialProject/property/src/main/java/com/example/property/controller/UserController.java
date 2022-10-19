package com.example.property.controller;

import com.example.property.entity.RegistrationRequest;
import com.example.property.entity.User;
import com.example.property.repository.UserRepository;
import com.example.property.service.RegistrationService;
import com.example.property.service.UserService;
import com.example.property.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class UserController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;


    @Autowired
    private RegistrationService registrationService;

    @PostMapping("api/v1/registration")
    public String save(@RequestBody RegistrationRequest request) {

        return registrationService.register(request);
    }



    @GetMapping("/UpdatePassword")
    public String showUpdateForm(Model model) {
        return "Post form";
    }

    @PutMapping("/@/update/{email}")
    public String findByPassword(String email)  {
        String update = userService.save(email);
        return "updated";

    }

    @GetMapping("find/{email}")
    public Optional<User> findByEmail(@PathVariable String email) {

        Optional<User> user = userService.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Email not found");
        }
        return user;
    }

    @RequestMapping("/user")
    public String login(@RequestBody User user){
        return "login successfully";
    }
}



