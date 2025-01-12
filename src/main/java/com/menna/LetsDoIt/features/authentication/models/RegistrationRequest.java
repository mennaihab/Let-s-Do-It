package com.menna.LetsDoIt.features.authentication.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record RegistrationRequest(
        @Size(min = 8) String name,
        @Email String email,
        @Min(8) String password) {
}