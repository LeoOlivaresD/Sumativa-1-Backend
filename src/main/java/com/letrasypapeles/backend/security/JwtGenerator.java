package com.letrasypapeles.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtGenerator {
    private static final Key key = Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET.getBytes());

    // Metodo para crear un token por medio de la autenticacion
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currenDate = new Date(); // Establecemos el tiempo actual del momento
        Date expirationDate = new Date(currenDate.getTime() + SecurityConstants.JWT_EXPIRATION_TIME);
        // Linea para generar el token
        String token = Jwts.builder() // Construimos un token JWT llamado token
                .setSubject(username) // Aca establecemos el nombre de usuario que está iniciando sesión
                .setIssuedAt(new Date()) // Establecemos la fecha de emisión del token en el momento actual
                .setExpiration(expirationDate) // Establecemos la fecha de caducidad del token
                .signWith(key, SignatureAlgorithm.HS512)
                .compact(); // Este método finaliza la construcción del token y lo convierte en una cadena
        return token;
    }

    // Metodo para extraer un username a partir de un token
    public String getUsernameFromJwtToken(String token) {
        Claims claims = Jwts.parserBuilder() // El método parser se utiliza con el fin de analizar el token
                .setSigningKey(key)// Establece la clave de firma, que se utiliza para verificar la firma del token
                .build()
                .parseClaimsJws(token) // Se utiliza para verificar la firma del token, apartir del String "token"
                .getBody();
        return claims.getSubject();// Devolvemos el nombre de usuario
    }

    // Metodo para validar nuestro token
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
