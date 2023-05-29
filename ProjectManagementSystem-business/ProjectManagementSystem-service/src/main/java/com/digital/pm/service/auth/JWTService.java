package com.digital.pm.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor

public class JWTService {
    private final CustomUserDetailService customUserDetailService;
    @Value("${secret_key}")
    private String SECRET_KEY;

    public String extractUserAccount(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(Map<String, Object> extractClaims, Authentication authentication) {
        return Jwts.builder().
                setClaims(extractClaims).
                setSubject(authentication.getName()).
                setIssuedAt(new Date(System.currentTimeMillis())).
                setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)).
                signWith(getSigningKey(), SignatureAlgorithm.HS256).
                compact();
    }

    public String generateToken(Authentication authentication) {
        return generateToken(new HashMap<>(), authentication);
    }

    public boolean isTokenValid(String token) {
        String userAccount = extractUserAccount(token);
        var userDetails = customUserDetailService.loadUserByUsername(userAccount);

        return (userAccount.equals(userDetails.getUsername()));
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        var claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();

    }

    private Key getSigningKey() {
        var currentKey = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(currentKey);
    }

}
