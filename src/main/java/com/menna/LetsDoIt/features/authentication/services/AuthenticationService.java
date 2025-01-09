package com.menna.LetsDoIt.features.authentication.services;

import com.menna.LetsDoIt.features.authentication.models.AuthenticationResponse;
import com.menna.LetsDoIt.features.authentication.models.LoginRequest;
import com.menna.LetsDoIt.features.authentication.models.RegistrationRequest;
import com.menna.LetsDoIt.features.common.models.User;
import com.menna.LetsDoIt.features.common.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(RegistrationRequest request) throws Exception {
        var newUser = User.builder()
                .email(request.email())
                .name(request.name())
                .password(passwordEncoder.encode(request.password()))
                .build();
        userRepository.saveAndFlush(newUser);
        var jwtToken = jwtService.generateJwtToken(newUser);
        return AuthenticationResponse.builder().token(jwtToken).build();

    }

    public AuthenticationResponse login(LoginRequest request) {
        var object = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(request.email(), request.password())
        );
        User user = (User) object.getPrincipal();

        var jwtToken = jwtService.generateJwtToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

}
