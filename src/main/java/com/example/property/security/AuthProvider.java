package com.example.property.security;

import com.example.property.controller.Attempts;
import com.example.property.entity.User;
import com.example.property.repository.AttemptsRepository;
import com.example.property.repository.UserRepository;
import com.example.property.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Optional;


@Component
public class AuthProvider implements AuthenticationProvider, Serializable {
    private static  final int ATTEMPTS_LIMIT= 3;
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AttemptsRepository attemptsRepository;

    @Autowired
    private UserRepository userRepository;





    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        Optional<Attempts> userAttempts= attemptsRepository
                .findAttemptsByUsername(username);
        if(userAttempts.isPresent()){
            Attempts attempts = userAttempts.get();
            attempts.setAttempts(0);
            attemptsRepository.save(attempts);
        }
return null;
    }

    @Bean
    private User processFailedAttempts(String username, User user) {

        Optional<Attempts> userAttempts = attemptsRepository.findAttemptsByUsername(username);
        if(userAttempts.isPresent()){
            Attempts attempts = new Attempts();
            attempts.setUsername(username);
            attempts.setAttempts(1);
            attemptsRepository.save(attempts);
        }else  if(userAttempts.isPresent()){
            Attempts attempts = userAttempts.get();
            attempts.setAttempts(attempts.getAttempts() +1);
            attemptsRepository.save(attempts);

            if(attempts.getAttempts() +1 > ATTEMPTS_LIMIT){
                userRepository.save(user);
                throw  new LockedException(
                        "TOO MANY INVALID ATTEMPTS. Account is locked."
                );
            }
        }
        return user;
    }


    @Override public boolean supports(Class<?> authentication) {
        return true;
    }

    @Bean
    public User username() {
        return new User();
    }
}


