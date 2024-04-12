package com.example.registration;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;


class RegisterServiceTest {

    @Test
    void register() {

        TimeService timeService = mock(TimeService.class);
        LocalDateTime fixedTime = LocalDateTime.of(2023, Month.JANUARY, 1, 0, 0);
        Mockito.when(timeService.now()).thenReturn(fixedTime);


        // Your existing code
        RegisterRepository repository = mock(RegisterRepository.class);
        TokenService tokenService = mock(TokenService.class);
        RegisterService service = new RegisterService(repository, timeService, tokenService);
        UserRequest req = new UserRequest();
        req.setEmail("correo");
        req.setPassword("password");
        req.setPhones(Collections.emptyList());

        when(tokenService.getToken(req.getPassword())).thenReturn("token");


        UserDAO testUser = UserDAO.builder()
                .email(req.getEmail())
                .token("token")
                .isActive(true) // Consider how you want to manage this field
                .created(timeService.now())
                .modified(timeService.now())
                .lastLogin(timeService.now())
                // Here you can generate random UUID
//                .id(UUID.randomUUID())
                .phones(req.getPhones().stream().map(
                                phone -> PhoneEntity.builder()
                                        .number(phone.getNumber())
                                        .citycode(phone.getCitycode())
                                        .countrycode(phone.getContrycode())
                                        .build()
                        )
                        .collect(Collectors.toList()))
                .build();

        when(repository.existsByEmail("correo")).thenReturn(false);
        when(repository.save(testUser)).thenReturn(testUser);


        service.registerUser(req);

        // Verify that the method was called on the repository mock

        verify(repository).save(testUser);
    }
}
