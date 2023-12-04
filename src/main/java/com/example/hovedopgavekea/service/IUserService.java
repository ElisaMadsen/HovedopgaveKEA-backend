package com.example.hovedopgavekea.service;

import com.example.hovedopgavekea.model.User;

import java.util.Optional;

public interface IUserService extends ICRUDService<User, Long> {
    Optional<User> findByUserEmailAndUserPassword(String userEmail, String userPassword);

}
