package com.email.interfaces.services;

import com.email.interfaces.dtos.SignUpForm;
import com.email.interfaces.entities.User;
import com.email.interfaces.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Boolean verifyExist(String email) {

        return userRepository.findByEmail(email).isPresent();
    }


    public void registerNewUser(SignUpForm signUpForm) {

        userRepository.save(User.builder()
                .email(signUpForm.getEmail())
                .password(signUpForm.getPassword())
                .build());

    }
}
