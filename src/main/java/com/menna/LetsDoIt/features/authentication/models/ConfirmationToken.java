package com.menna.LetsDoIt.features.authentication.models;

import com.menna.LetsDoIt.features.common.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    @Column
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
            name = "user_Id",
            nullable = false
    )
    private User user;
}
