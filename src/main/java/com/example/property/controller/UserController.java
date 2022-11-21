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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin

public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;



    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("api/v1/registration")
    public String save(@ModelAttribute("User") RegistrationRequest request) {

        return registrationService.register(request);
    }



    @GetMapping("/UpdatePassword")
    public String showUpdateForm(Model model) {
        return "Post form";
    }

   /* @PutMapping("/@/update/{email}")
    public User findByPassword(String email)  {
        User update = userServiceImpl.save(email);
        return update;

    }*/

    @GetMapping("find/{email}")
    public User findByEmail(@PathVariable String email) {

        User user = userServiceImpl.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Email not found");
        }
        return user;
    }

    @RequestMapping("/user")
    public String login(@RequestBody User user){
        return "login successfully";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("errorMsg", "Your username and password are invalid.");

        if (logout != null)
            model.addAttribute("msg", "You have been logged out successfully.");

        return "login";
    }
}



