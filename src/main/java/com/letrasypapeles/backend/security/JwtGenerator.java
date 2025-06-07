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
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    // Metodo para crear un token por medio de la autenticacion
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currenDate = new Date(); //Establecemos el tiempo actual del momento
        Date expirationDate = new Date(currenDate.getTime() + SecurityConstants.JWT_EXPIRATION_TIME); //Aca establecemos el tiempo que de expiracion apartir del tiempo actual que se dio antes

        //Aca comenzamos a crear nuestro token
        String token = Jwts.builder()
                .setSubject(username) //Se pasa el nombre del usuario
                .setIssuedAt(new Date())   // se indica el inicio de nuestro token
                .setExpiration(expirationDate) //se indica la expiracion del token
                .signWith(key, SignatureAlgorithm.HS512) //usamos la firma que creamos en nuestras constantes
                .compact();

        return token;
    }
                // Metodo para extraer un username a partir de un token 
                // 
    public String getUsernameFromJwtToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
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
