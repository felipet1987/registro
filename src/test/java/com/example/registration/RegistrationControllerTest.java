package com.example.registration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class RegistrationControllerTest {


    private RegisterService registerService;

    private RegistrationController registrationController;


    @BeforeEach
    void setUp() {
        registerService = mock(RegisterService.class);
        registrationController = new RegistrationController(registerService);
    }

    @Test
    void name() {
        UserRequest payload = new UserRequest();
        payload.setEmail("name@email.com");
        payload.setPassword("Abcdef12");

        // Assuming UserResponse has an "id" or any other field.
        UserResponse expectedResponse = UserResponse.builder().id(UUID.randomUUID()).build();

        when(registerService.registerUser(payload)).thenReturn(expectedResponse);

        UserResponse actualResponse = registrationController.register(payload);

        // Asserting that the actual response matches the expected response
        assertEquals(expectedResponse.getId(), actualResponse.getId());

        Mockito.verify(registerService).registerUser(Mockito.any(UserRequest.class));
    }
}