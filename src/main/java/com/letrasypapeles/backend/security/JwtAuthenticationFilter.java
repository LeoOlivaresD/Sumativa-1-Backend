package com.letrasypapeles.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*La función de esta clase será validar la información del token y si esto es exitoso,
establecerá la autenticación de un usuario en la solicitud o en el contexto de seguridad de nuestra aplicación*/
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailService customUsersDetailsService;
    @Autowired
    private JwtGenerator jwtGenerador;

    // Obtendremos token desde solicitud
    private String obtenerTokenDeSolicitud(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("Ejecutando JwtAuthenticationFilter...");
        // 🔹 Excluir la validación del token para el endpoint de login
        if (request.getRequestURI().equals("/api/auth/login")) {
            System.out.println("Excluyendo autenticación JWT para el login...");
            filterChain.doFilter(request, response);
            return;
        }
        String path = request.getRequestURI();

        // Excluir rutas públicas (sin necesidad de token)
        if (path.startsWith("/api/auth/")) {
            filterChain.doFilter(request, response);
            return; // Salir del filtro, no validar token
        }

        String token = obtenerTokenDeSolicitud(request);
        System.out.println("Token recibido: " + token);
        if (StringUtils.hasText(token) && jwtGenerador.validateJwtToken(token)) {
            String username = jwtGenerador.getUsernameFromJwtToken(token);
            System.out.println("Autenticando usuario: " + username);
            UserDetails userDetails = customUsersDetailsService.loadUserByUsername(username);
            System.out.println("Roles del usuario: " + userDetails.getAuthorities());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null,
                    userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }

}
