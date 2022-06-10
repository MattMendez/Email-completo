package com.email.interfaces.repositories;

import com.email.interfaces.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String>{

    Optional<User> findByEmail(String email);

}
