package com.example.property.controller;


import com.example.property.repository.UserRepository;
import com.example.property.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ForgotPasswordController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserRepository usersRepository;


    @GetMapping("/forgot_password")
    public String showForgotPasswordForm(){
        return "forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String processForgetPassword(HttpServletRequest request, Model model) {
        return "forgot_password_form";
    }

    public void sendEmail(String email, String resetPasswordLink){

    }
    public void sendEmail(){

    }



    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {

        return "message";
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(String token, Model model) {

        return "reset_password_form";
    }


}
