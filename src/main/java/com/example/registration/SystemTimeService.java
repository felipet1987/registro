package com.example.registration;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SystemTimeService implements TimeService {
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
