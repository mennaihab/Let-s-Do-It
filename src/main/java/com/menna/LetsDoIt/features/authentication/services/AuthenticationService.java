package com.menna.LetsDoIt.features.authentication.services;

import com.menna.LetsDoIt.features.authentication.models.AuthenticationResponse;
import com.menna.LetsDoIt.features.authentication.models.ConfirmationToken;
import com.menna.LetsDoIt.features.authentication.models.LoginRequest;
import com.menna.LetsDoIt.features.authentication.models.RegistrationRequest;
import com.menna.LetsDoIt.features.authentication.repos.ConfirmationTokenRepository;
import com.menna.LetsDoIt.features.common.models.Roles;
import com.menna.LetsDoIt.features.common.models.User;
import com.menna.LetsDoIt.features.common.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ConfirmationTokenService confirmationTokenService;
    private final MailSenderService mailSenderService;
    private final ConfirmationTokenRepository confirmationTokenRepository;


    public String register(RegistrationRequest request)  {
        var newUser = User.builder()
                .email(request.email())
                .name(request.name())
                .password(passwordEncoder.encode(request.password()))
                .role(Roles.USER)
                .enabled(false)
                .build();
        userRepository.saveAndFlush(newUser);
        ConfirmationToken confirmationToken = confirmationTokenService.generateConfirmationToken(newUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        String confirmationLink ="http://letsdoit.com/auth/api/confirm?confirmationToken=" +confirmationToken.getToken();
        String mailBody = mailSenderService.buildEmail(newUser.getName(),confirmationLink);
        mailSenderService.send(mailBody, newUser.getEmail());
        return "email sent";
    }

    public AuthenticationResponse login(LoginRequest request) {

        var object = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(request.email(), request.password())
        );

        User user = (User) object.getPrincipal();

        var jwtToken = jwtService.generateJwtToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public String confirm(String token) {
       var confirmationToken = confirmationTokenRepository.findByToken(token);
      if (confirmationToken.isPresent()){
          if(confirmationToken.get().getExpiredAt().isAfter(LocalDateTime.now())) {
              confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
              String userEmail = confirmationToken.get().getUser().getEmail();
              userRepository.updateEnabled(userEmail);
              return "confirmed";
          } else {
              return "token expired";
          }
      } else {
          System.out.println("token not found");
          return "cannot confirm";
      }
    }
}
