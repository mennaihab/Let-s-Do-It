package com.menna.LetsDoIt.features.authentication.models;

import jakarta.validation.constraints.Email;

public record LoginRequest(@Email String email, String password){}
