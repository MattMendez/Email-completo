package com.email.interfaces.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "users")
public class User {

    @Id()
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Email(message = "The Email must be valid")
    @Column(unique = true)
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

    @OneToMany
    private List<com.email.interfaces.entities.Email> emailsSended;

}
