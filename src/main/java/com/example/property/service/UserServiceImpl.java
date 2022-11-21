package com.example.property.service;

import com.example.property.entity.User;
import com.example.property.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@AllArgsConstructor
@Setter

public  class  UserServiceImpl implements UserService, UserDetailsService {

    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JavaMailSender mailSender;
    private UserRepository userService;

    public UserServiceImpl() {

    }


    @Override
    public User findByEmail(String email) {

        User user;
         user = usersRepository.findByEmail(email);
        user.add(linkTo(methodOn(UserServiceImpl.class).findByEmail(email)).withSelfRel());
        if (user == null) {
            throw new UsernameNotFoundException("Email not found");
        }

        return user;

    }

    @Override
    public String save(User user) {
        User userExists = usersRepository.findByEmail(user.getEmail());

        if (userExists != null ) {
            throw new IllegalStateException("email already taken");
        }
        String encodedPassword = bCryptPasswordEncoder
                .encode(user.getPassword());

        user.setPassword(encodedPassword);
        usersRepository.save(user);
        return "registered successfully";
    }

    @Override
    public User save(String email) {
        User user = usersRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Email not found");
        }

        User users = usersRepository.save(user);
        return users;
    }

    @Override
    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException{
      User user=usersRepository.findByEmail(email);
        if (user != null){
            user.setResetPasswordToken(token);
            usersRepository.save(user);
        }else{
            throw  new UsernameNotFoundException("Could not find any user with the email" + email);
        }

    }



    public User getByResetPasswordToken(String token){
        return usersRepository.findByResetPasswordToken(token);
    }

   /* public void updatePassword(String user, Long newPassword){
        BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        usersRepository.save(user);
    }*/

   /* public String processForgetPassword(HttpServletRequest request, Model model) throws UnsupportedEncodingException, MessagingException {
        String email = request.getParameter("email");
        String token = RandomString.make(30);
        try {

            usersRepository.resetPasswordToken(token);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        return "forgot_password_form";
    }*/
    /*private void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("contact@mypro.com", "Mypro Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p> Hello, </p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);

    }
*/
    @Override
    public String showResetPasswordForm(String token, Model model) {
        String user = usersRepository.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if(user==null){
            model.addAttribute("message", "invalid Token");
            return "message";
        }
        return "reset_password_form";
    }

    @Override
     public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        String user = usersRepository.getByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");
        if(user==null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }else{
            usersRepository.resetPasswordToken(password);
            model.addAttribute("message", "You have successfully changed your password.");
        }
        return "message";
    }

    @Override
    public void updatePassword(String user, Long newPassword) {

    }


    /*
    javascript code to validate the match of two password fields

    function checkPasswordMatch(fieldConfirmPassword) {
    if (fieldConfirmPassword.value != $("#password").val()) {
        fieldConfirmPassword.setCustomValidity("Passwords do not match!");
    } else {
        fieldConfirmPassword.setCustomValidity("");
    }
}*/

    /*@Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return (UserDetails) usersRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException
                        (String.format(USER_NOT_FOUND_MSG)));
    }*/

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
       UserDetails userDetails= usersRepository.findByEmail(email);
                if (userDetails==null){
                    throw new UsernameNotFoundException("Username not found ");
                }
                 return userDetails;
    }

    public ResponseEntity<List<User>> findAll() {
        try{
            List<User> list = usersRepository.findAll();
            if(list.isEmpty() || list.size() == 0){
                return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<User>>(list, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR );
        }

    }



}
