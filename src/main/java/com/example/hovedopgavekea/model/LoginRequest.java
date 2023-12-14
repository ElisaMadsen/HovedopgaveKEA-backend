package com.example.hovedopgavekea.model;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoginRequest {
    private String userEmail;
    private String userPassword;
}
