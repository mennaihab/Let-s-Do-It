package com.menna.LetsDoIt.features.authentication.models;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Builder
public record AuthenticationResponse(String token){}
