package com.example.registration;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder

public class UserResponse {

    private UUID id;


    private String email;

    private String token;


    private LocalDateTime created;


    private LocalDateTime modified;


    private LocalDateTime lastLogin;


    private Boolean isActive;
}
