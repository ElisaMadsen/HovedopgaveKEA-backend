package com.example.hovedopgavekea.repository;

import com.example.hovedopgavekea.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserEmailAndUserPassword(String userEmail, String userPassword);

}
