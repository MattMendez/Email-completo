package com.email.interfaces.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpForm {

    @Email
    private String email;

    private String password;

    private String name;

    private String lastName;

    private String address;

    private String phoneNumber;

    private String city;

    private String state;

    private String country;

    private String backUpEmail;

}