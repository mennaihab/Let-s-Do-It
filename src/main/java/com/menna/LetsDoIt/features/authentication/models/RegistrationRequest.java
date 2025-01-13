package com.menna.LetsDoIt.features.authentication.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistrationRequest(
      @NotBlank @Size(min = 8) String name,
      @NotBlank @Email String email,
      @NotBlank @Min(8) String password) {
}