package com.DriverMileageTracker.Backend.Controller.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {
    private static final String TOKEN_TYPE_CLAIM = "tokenType";
    private static final String ACCESS_TOKEN = "access";
    private static final String REFRESH_TOKEN = "refresh";

    @Value("${jwt.secret-key}")
    private String secret;

    @Value("${jwt.access-expiration-ms:900000}")
    private long accessExpirationMs;

    @Value("${jwt.refresh-expiration-ms:604800000}")
    private long refreshExpirationMs;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(String username, Collection<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .claim(TOKEN_TYPE_CLAIM, ACCESS_TOKEN)
                .claim("roles", List.copyOf(roles))
                .setExpiration(new Date(System.currentTimeMillis() + accessExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .claim(TOKEN_TYPE_CLAIM, REFRESH_TOKEN)
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateAccessToken(String token, UserDetails userDetails) {
        String extractedUsername = extractUsername(token);
        return extractedUsername != null
                && extractedUsername.equals(userDetails.getUsername())
                && ACCESS_TOKEN.equals(extractTokenType(token))
                && !isTokenExpired(token);
    }

    public boolean validateRefreshToken(String token, UserDetails userDetails) {
        String extractedUsername = extractUsername(token);
        return extractedUsername != null
                && extractedUsername.equals(userDetails.getUsername())
                && REFRESH_TOKEN.equals(extractTokenType(token))
                && !isTokenExpired(token);
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractTokenType(String token) {
        return extractClaims(token).get(TOKEN_TYPE_CLAIM, String.class);
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
