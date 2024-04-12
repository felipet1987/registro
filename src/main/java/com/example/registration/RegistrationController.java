package com.example.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@AllArgsConstructor
@RestController
public class RegistrationController {

    RegisterService registerService;


    @PostMapping("/register")
    public UserResponse register(@RequestBody UserRequest payload) {


        if(!validateEmail(payload.getEmail())) {
            throw new IllegalArgumentException("Email format is not valid");
        }

        if(!validatePassword(payload.getPassword())) {
            throw new IllegalArgumentException("Password format is not valid");
        }

        return registerService.registerUser(payload);
    }


    public boolean validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        return emailPattern.matcher(email).matches();
    }

    public boolean validatePassword(String password) {
        String passwordRegex = "^(?=(?:[^0-9]*[0-9]){2})(?=.*[A-Z])[a-zA-Z0-9]{8,12}$";
        Pattern passwordPattern = Pattern.compile(passwordRegex);
        return passwordPattern.matcher(password).matches();
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

@Data
class ErrorResponse {
    private String mensaje;

    public ErrorResponse(String mensaje) {
        this.mensaje = mensaje;
    }

    // getters and setters
}