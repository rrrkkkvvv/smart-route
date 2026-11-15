package com.rrrkkkvvv.smartRoute.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    @Value("${security.jwt.secret-key}")
    private String secretKey;
    private final long EXPIRATION_TIME = (1000 * 60 * 60)*2;
    @PostConstruct
    public void checkEnvironment() {
        System.out.println("JWT_SECRET loaded: " + (secretKey != null));
    }
    private final ObjectMapper objectMapper = new ObjectMapper();

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String buildToken(JwtUserPrincipal jwtUserPrincipal){
        String subjectJson = objectMapper.writeValueAsString(jwtUserPrincipal);

        return Jwts
                .builder()
                .subject(subjectJson)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignInKey())
                .compact();
    }
    public JwtUserPrincipal extractUserData(String token) {
        return objectMapper.readValue(getClaimsFromToken(token).getSubject(), JwtUserPrincipal.class);
    }

    private Boolean isTokenExpired(final String token) {
        final Date expiration = getClaimsFromToken(token).getExpiration();
        return expiration.before(new Date());
    }
    public boolean isTokenValid(String token, JwtUserPrincipal user) {
        final JwtUserPrincipal userData = extractUserData(token);
        return userData.id().equals(user.id())
                && userData.email().equals(user.email())
                && !isTokenExpired(token);
    }
    private Claims getClaimsFromToken(final String token) {
        return Jwts
                .parser()
                .verifyWith( getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
