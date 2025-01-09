package com.menna.LetsDoIt.features.authentication.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {
    public String generateJwtToken(UserDetails userDetails) {
        final var expiry = Duration.ofDays(1).getSeconds() * 1000;
        return Jwts
                .builder()
                .signWith(getSigningKey(),SignatureAlgorithm.HS256)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ expiry))
                .setSubject(userDetails.getUsername())
                .compact();

    }

    public boolean isExpired(String token) {
        Date expirationDate = getExpiration(token);
       return expirationDate.before(new Date());
    }

    private Date getExpiration(String token) {
       return getClaims(token).getExpiration();
    }
    public String getEmail(String token) {
       return  getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    private Key getSigningKey() {
        String SECRET_KEY = "qO9pDAzTjPUzTxdyEwuTviWhlL6XdSM+OS2dAH8Sbb2r5Ns0jgQYaJkZAm/f9/Mp";
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);

    }
}
