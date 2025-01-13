package com.menna.LetsDoIt.features.authentication.services;

import com.menna.LetsDoIt.features.authentication.models.ConfirmationToken;
import com.menna.LetsDoIt.features.authentication.repos.ConfirmationTokenRepository;
import com.menna.LetsDoIt.features.common.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    public ConfirmationToken generateConfirmationToken(User user) {
        String token = UUID.randomUUID().toString();
        return  ConfirmationToken.builder()
                .token(token)
                .issuedAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(60))
                .user(user)
                .build();

    }

    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.saveAndFlush(confirmationToken);



    }
}
