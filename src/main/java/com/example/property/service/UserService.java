package com.example.property.service;


import com.example.property.entity.User;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;


public interface UserService {
    User findByEmail(String email);
    String save(User user);
    User save(String email);
  public void updateResetPasswordToken(String token, String email);

   public String showResetPasswordForm(String token, Model model);

    public String processResetPassword(HttpServletRequest request, Model model);

}
