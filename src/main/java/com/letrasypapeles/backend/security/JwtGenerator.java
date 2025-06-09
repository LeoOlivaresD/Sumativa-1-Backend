package com.letrasypapeles.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

@Component
public class JwtGenerator {
    private static final SecretKey key = Keys.hmacShaKeyFor(
            SecurityConstants.JWT_SECRET.getBytes(StandardCharsets.UTF_8));

    // Método para crear un token por medio de la authentication
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currenDate = new Date();
        Date expirationDate = new Date(currenDate.getTime() + SecurityConstants.JWT_EXPIRATION_TIME);
        // Linea para generar el token
        String token = Jwts.builder()
                .setSubject(username)// Aca establecemos el nombre de usuario que está iniciando sesión
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS512)// Utilizamos este método para firmar nuestro token
                .compact();

        return token;
    }

    // Método para extraer un Username apartir de un token
    public String getUsernameFromJwtToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // Método para validar el token
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("El token JWT ya no es válido o ha expirado", e);
        }
    }
}
