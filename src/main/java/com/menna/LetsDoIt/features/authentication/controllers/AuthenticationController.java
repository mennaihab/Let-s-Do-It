package com.menna.LetsDoIt.features.authentication.controllers;

import com.menna.LetsDoIt.features.authentication.models.AuthenticationResponse;
import com.menna.LetsDoIt.features.authentication.models.LoginRequest;
import com.menna.LetsDoIt.features.authentication.models.RegistrationRequest;
import com.menna.LetsDoIt.features.authentication.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @ModelAttribute RegistrationRequest request) throws Exception {
        return ResponseEntity.ok().body(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @ModelAttribute LoginRequest request) {
        return ResponseEntity.ok().body(authenticationService.login(request));
    }
}
