package com.rpovlsen.webshopapi.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

// JWT Ensures that authentication tokens are created by a trusted source (us)
@Component
public class JwtTokenUtil
{
    // JWT Token is valid for 5 hours
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt.secret}") // Gets the JWT secret from application.properties
    private String secret;

    public String genToken(Map<String, Object> claims, String subject)
    {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (JWT_TOKEN_VALIDITY * 1000)))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    // Jwts framework decodes the token, and extracts the claims.
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public boolean isTokenExpired(String token)
    {
        Date expiration = getClaimFromToken(token, Claims::getExpiration);
        return expiration.before(new Date(System.currentTimeMillis()));
    }

    public boolean isTokenValid(String token)
    {
        try
        {
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);

            // If no exception is thrown, assume token is valid.
            return true;
        }
        catch (JwtException e)
        {
            // If this exception is thrown, assume token is invalid and needs to be regenerated.
            return false;
        }
    }

    public String getUsernameFromToken(String token)
    {
        return getClaimFromToken(token, Claims::getSubject);
    }
}
