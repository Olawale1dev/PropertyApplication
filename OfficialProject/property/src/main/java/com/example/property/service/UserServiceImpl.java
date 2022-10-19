package com.example.property.service;

import com.example.property.entity.User;
import com.example.property.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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


    @Override
    public Optional<User> findByEmail(String email) {

        Optional<User> user = usersRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Email not found");
        }

        return user;

    }

    @Override
    public String save(User user) {
        boolean userExists = usersRepository.findByEmail(user.getEmail())
                .isPresent();

        if (userExists) {
            throw new IllegalStateException("email already taken");
        }
        String encodedPassword = bCryptPasswordEncoder
                .encode(user.getPassword());

        user.setPassword(encodedPassword);
        usersRepository.save(user);
        return "registered successfully";
    }

    @Override
    public String save(String email) {
        Optional<User> user = usersRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Email not found");
        }

        String users = usersRepository.save(user);
        return users;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return (UserDetails) usersRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException
                        (String.format(USER_NOT_FOUND_MSG)));
    }


}
