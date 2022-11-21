package com.example.property.entity;


import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class User extends RepresentationModel<User> implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name",  length = 20)
    private String firstName;
    @Column(name = "last_name",  length = 20)
    private String lastName;
    @Column( unique = false, length = 45)
    private String email;
    @Column(name="Password",  unique = true)
    private String password;
    @Column(name = "gender_title")
    private String genderTitle;
    @Column(name = "signup_as")
    private String signUpAs;
    private String state;
    private String city;

    private String locality;

    private String zip;
    @Column(name="update_reset_password_token")
    private String resetPasswordToken;
    private boolean accountNonLocked;


// constructor used by Registration service class
    public User(
            String firstName,
            String lastName,
            String email,
            String password,
            String genderTitle,
            String signUpAs,
            String state,
            String city,
            String zip) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.genderTitle=genderTitle;
        this.signUpAs = signUpAs;
        this.city = city;
        this.state = state;
        this.zip = zip;

    }


// this granted authority to user to login as user
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(getUsername());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGenderTitle() {
        return genderTitle;
    }

    public String getSignUpAs(){
        return signUpAs;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setAccountNonLocked(Boolean accountNonLocked){
        this.accountNonLocked = accountNonLocked;
    }
    public boolean getAccountNonLocked(){
        return accountNonLocked;
    }

}

