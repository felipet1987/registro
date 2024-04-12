package com.example.registration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.stream.Collectors;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@AllArgsConstructor
@Service
public class RegisterService {
    private static final String SECRET_KEY = "your-secret-key";

    RegisterRepository repository;
    TimeService timeService;
    TokenService tokenService;
    public UserResponse registerUser(UserRequest req) {
        boolean existsByEmail = repository.existsByEmail(req.getEmail());
        if (existsByEmail) {
            throw new IllegalArgumentException("Email already exists");
        }

        String token = tokenService.getToken(req.getPassword());

        UserDAO user = UserDAO.builder()
                .email(req.getEmail())
                .token(token)
                .isActive(true) // Consider how you want to manage this field
                .created(timeService.now())
                .modified(timeService.now())
                .lastLogin(timeService.now())
                .phones(req.getPhones().stream().map(
                                phone -> PhoneEntity.builder()
                                        .number(phone.getNumber())
                                        .citycode(phone.getCitycode())
                                        .countrycode(phone.getContrycode())
                                        .build()
                        )
                        .collect(Collectors.toList()))
                .build();

        UserDAO returnUser = repository.save(user);



        return UserResponse.builder()
                .email(returnUser.getEmail())
                .isActive(returnUser.getIsActive())
                .created(returnUser.getCreated())
                .modified(returnUser.getModified())
                .lastLogin(returnUser.getLastLogin())
                .id(returnUser.getId())
                .token(returnUser.getToken())
                .build();
    }
}

