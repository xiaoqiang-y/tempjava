package com.example.movieticketsystem.service;

import com.example.movieticketsystem.model.entity.User;
import com.example.movieticketsystem.model.request.AuthenticationRequest;
import com.example.movieticketsystem.model.request.RegisterRequest;
import com.example.movieticketsystem.model.response.AuthenticationResponse;

public interface AuthService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    User getCurrentUser();
}    